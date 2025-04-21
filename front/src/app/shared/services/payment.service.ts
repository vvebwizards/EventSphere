import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payment } from '../models/payment.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  /**
   * Process payment through backend API
   */
  processPayment(payment: Payment): Observable<{ success: boolean, transactionId?: string }> {
    return this.http.post<{ success: boolean, transactionId?: string }>(`${this.apiUrl}/payments`, payment);
  }

  /**
   * Validate email format
   */
  validateEmail(email: string): boolean {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailPattern.test(email);
  }

  /**
   * Format credit card number with spaces
   */
  formatCardNumber(cardNumber: string): string {
    // Remove all non-digits
    const cleanedNumber = cardNumber.replace(/\D/g, '');
    
    // Add a space after every 4 digits
    const formattedNumber = cleanedNumber.replace(/(\d{4})(?=\d)/g, '$1 ');
    
    return formattedNumber;
  }

  /**
   * Validate credit card number using Luhn algorithm
   */
  validateCardNumber(cardNumber: string): boolean {
    // Remove all spaces and non-digits
    const cleanedNumber = cardNumber.replace(/\D/g, '');
    
    // Basic length check
    if (cleanedNumber.length < 13 || cleanedNumber.length > 19) {
      return false;
    }

    // Implement Luhn algorithm check
    let sum = 0;
    let shouldDouble = false;
    
    // Loop through values starting from the rightmost digit
    for (let i = cleanedNumber.length - 1; i >= 0; i--) {
      let digit = parseInt(cleanedNumber.charAt(i));
      
      if (shouldDouble) {
        digit *= 2;
        if (digit > 9) {
          digit -= 9;
        }
      }
      
      sum += digit;
      shouldDouble = !shouldDouble;
    }
    
    return sum % 10 === 0;
  }
}