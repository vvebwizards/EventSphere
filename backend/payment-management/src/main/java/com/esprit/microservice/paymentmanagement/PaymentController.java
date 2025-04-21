package com.esprit.microservice.paymentmanagement;

import com.esprit.microservice.paymentmanagement.config.KeycloakConfig;
import com.esprit.microservice.paymentmanagement.config.KeycloakSecurityConfig;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.validation.Valid;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @RequestMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPayment(@Valid @RequestBody Payment payment , KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("user");
        if (hasUserRole) {
            try {
                PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                        .setAmount((long) (payment.getAmount() * 100))
                        .setCurrency("usd")
                        .setReceiptEmail(payment.getPayer())
                        .build();

                PaymentIntent intent = PaymentIntent.create(params);

                payment.setTimestamp(LocalDateTime.now());

                payment.setStatus(PaymentStatus.PENDING);


                Payment savedPayment = paymentService.save(payment);


                Map<String, Object> response = new HashMap<>();
                response.put("payment", savedPayment);
                response.put("clientSecret", intent.getClientSecret());

                return ResponseEntity.ok(response);

            } catch (StripeException e) {
                return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
            }
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }



    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAll();
    }

    @GetMapping("/get/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getById(id).orElse(null);
    }

    @DeleteMapping(value = "/admin/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deletePayment(@PathVariable(value = "id") Long id , KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        boolean hasUserRole = context.getToken().getRealmAccess().isUserInRole("user");
        if (hasUserRole) {
            paymentService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
