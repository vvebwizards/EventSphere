import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PaymentService } from '../shared/services/payment.service';
import { Payment, CreditCardInfo } from '../shared/models/payment.model';
import { ProgressBarComponent } from '../ui/progress-bar/progress-bar.component';
import { ButtonComponent } from '../ui/button/button.component';
import { FormFieldComponent } from '../ui/form-field/form-field.component';
import { LoadingSpinnerComponent } from '../ui/loading-spinner/loading-spinner.component';
import { ChangeDetectorRef } from '@angular/core'

@Component({
  selector: 'app-payment-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ProgressBarComponent,
    ButtonComponent,
    FormFieldComponent,
    LoadingSpinnerComponent
  ],
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent implements OnInit {
  paymentForm!: FormGroup;
  isSubmitting = false;
  paymentSuccess = false;
  transactionId = '';
  formProgress = 0;
  
  // Default values from the requirements
  defaultPayer = '';
  defaultAmount = 0;
  defaultMethod = '';

  constructor(
    private fb: FormBuilder,
    private paymentService: PaymentService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.initializeForm();
    this.updateFormProgress();
    
    // Subscribe to form value changes to update progress
    this.paymentForm.valueChanges.subscribe(() => {
      this.updateFormProgress();
    });
  }

  initializeForm(): void {
    this.paymentForm = this.fb.group({
      payer: [this.defaultPayer, [Validators.required, Validators.email]],
      amount: [this.defaultAmount, [Validators.required, Validators.min(0.01)]],
      method: [this.defaultMethod, Validators.required],
      cardInfo: this.fb.group({
        cardNumber: ['', [Validators.required, Validators.minLength(13)]],
        cardholderName: ['', Validators.required],
        expiryDate: ['', [Validators.required, Validators.pattern(/^(0[1-9]|1[0-2])\/\d{2}$/)]],
        cvv: ['', [Validators.required, Validators.pattern(/^\d{3,4}$/)]]
      })
    });
  }

  updateFormProgress(): void {
    const totalFields = 7; // Total number of fields in the form
    let completedFields = 0;
    
    // Count completed main fields
    ['payer', 'amount', 'method'].forEach(field => {
      if (this.paymentForm.get(field)?.valid) {
        completedFields++;
      }
    });
    
    // Count completed card info fields
    ['cardNumber', 'cardholderName', 'expiryDate', 'cvv'].forEach(field => {
      if (this.paymentForm.get('cardInfo')?.get(field)?.valid) {
        completedFields++;
      }
    });
    
    this.formProgress = Math.round((completedFields / totalFields) * 100);
  }

  onSubmit(): void {
    if (this.paymentForm.invalid) {
      this.markFormGroupTouched(this.paymentForm);
      return;
    }
    
    this.isSubmitting = true;
    
    const paymentData: Payment = {
      payer: this.paymentForm.get('payer')?.value,
      amount: this.paymentForm.get('amount')?.value,
      method: this.paymentForm.get('method')?.value
    };
    
    this.paymentService.processPayment(paymentData).subscribe({
      next: (response) => {
        console.log('Payment successful', response);
        // Assume success if we get a response
        this.paymentSuccess = true;
        this.transactionId = response.transactionId || 'N/A';
        this.isSubmitting = false;
      },
      error: (error) => {
        console.error('Payment failed', error);
        this.isSubmitting = false;
        // Optionally show error to user
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

  resetForm(): void {
    this.paymentForm.reset({
      payer: this.defaultPayer,
      amount: this.defaultAmount,
      method: this.defaultMethod
    });
    this.paymentSuccess = false;
    this.transactionId = '';
    this.updateFormProgress();
  }

  onCardNumberInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\s/g, '');
    
    if (value.length > 16) {
      value = value.substr(0, 16);
    }
    
    // Format with spaces
    const formattedValue = this.paymentService.formatCardNumber(value);
    
    // Update the input value
    this.paymentForm.get('cardInfo.cardNumber')?.setValue(formattedValue, { emitEvent: false });
  }

  onExpiryDateInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    
    if (value.length > 4) {
      value = value.substr(0, 4);
    }
    
    if (value.length > 2) {
      value = value.substr(0, 2) + '/' + value.substr(2);
    }
    
    this.paymentForm.get('cardInfo.expiryDate')?.setValue(value, { emitEvent: false });
  }

  onCvvInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    
    if (value.length > 4) {
      value = value.substr(0, 4);
    }
    
    this.paymentForm.get('cardInfo.cvv')?.setValue(value, { emitEvent: false });
  }

  // Helper method to mark all controls in a form group as touched
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      
      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      } else {
        control?.markAsTouched();
      }
    });
  }
}