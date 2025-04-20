import { Component } from '@angular/core';
import { AuthService } from '../shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    console.log(this.email);
    console.log(this.password);
    if (this.email && this.password) {
      this.authService.login(this.email, this.password).subscribe({
        next: (response) => {
          if (response.success) {
            localStorage.setItem('accessToken', response.accessToken);
            localStorage.setItem('refreshToken', response.refreshToken);
            this.router.navigate(['/profile']);
          } else {
            this.errorMessage = response.message;
          }
        },
        error: (err) => {
          this.errorMessage = err.error.message || 'An error occurred';
        },
      });
    } else {
      this.errorMessage = 'Please fill in both fields';
    }
  }
}
