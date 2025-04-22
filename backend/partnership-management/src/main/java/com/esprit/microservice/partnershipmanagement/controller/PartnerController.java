package com.esprit.microservice.partnershipmanagement.controller;

import com.esprit.microservice.partnershipmanagement.entity.Partner;
import com.esprit.microservice.partnershipmanagement.service.PartnerService;
import jakarta.annotation.security.RolesAllowed;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        Partner createdPartner = partnerService.createPartner(partner);
        return new ResponseEntity<>(createdPartner, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id) {
        return partnerService.getPartnerById(id)
                .map(partner -> new ResponseEntity<>(partner, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Partner>> getAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        Partner updatedPartner = partnerService.updatePartner(id, partner);
        return new ResponseEntity<>(updatedPartner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // New advanced search endpoint
    @GetMapping("/search")
    public ResponseEntity<List<Partner>> searchPartners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status) {
        List<Partner> partners = partnerService.searchPartners(name, status);
        return new ResponseEntity<>(partners, HttpStatus.OK);

    }
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getPartnerStatistics() {
        Map<String, Long> stats = partnerService.getPartnerStatistics();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}

