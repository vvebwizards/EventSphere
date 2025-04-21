import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reset-password',
  standalone: false,
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent {
  email: string = '';
  message: string = '';

  constructor(private http: HttpClient) {}

  onSubmit() {
    this.http.post<any>('http://localhost:3000/reset-password', { email: this.email }).subscribe({
      next: (res) => {
        this.message = res.message;
      },
      error: (err) => {
        this.message = "Erreur lors de l'envoi du lien.";
      }
    });
  }
}
