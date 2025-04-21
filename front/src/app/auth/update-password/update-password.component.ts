import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-update-password',
  standalone: false,
  templateUrl: './update-password.component.html',
})
export class UpdatePasswordComponent {
  newPassword = '';
  confirmPassword = '';
  errorMessage = '';
  successMessage = '';
  token = '';

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {
    this.route.queryParams.subscribe(params => {
      this.token = params['key'];
    });
  }

  onUpdatePassword() {
    if (this.newPassword !== this.confirmPassword) {
      this.errorMessage = "Les mots de passe ne correspondent pas.";
      return;
    }

    this.http.post('http://localhost:5000/api/auth/update-password', {
      token: this.token,
      newPassword: this.newPassword
    }).subscribe({
      next: () => {
        this.successMessage = "Mot de passe mis à jour avec succès.";
        setTimeout(() => this.router.navigate(['/login']), 3000);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || "Erreur lors de la mise à jour du mot de passe.";
      }
    });
  }
}
