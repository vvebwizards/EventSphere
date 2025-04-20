import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-sign-up',
  standalone: false,
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  signupForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.signupForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      userType: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
    
  }
  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    if (password !== confirmPassword) {
      form.get('confirmPassword')?.setErrors({ mismatch: true });
    } else {
      form.get('confirmPassword')?.setErrors(null);
    }
    return null;
  }
  
  ngOnInit(): void {}

  onSubmit(): void {
    if (this.signupForm.valid) {
      const { firstName, lastName, email, password, userType } = this.signupForm.value;
  
      const roleMap: { [key: string]: string } = {
        regular: 'user',
        resource_owner: 'resource-owner',
        event_creator: 'event-creator',
        partner: 'partner'
      };
  
      const mappedRole = roleMap[userType];
  
      this.authService
        .signup({ firstName, lastName, email, password, role: mappedRole })
        .subscribe({
          next: (response) => {
            if (response.success) {
              this.router.navigate(['/login']);
            } else {
              this.errorMessage = response.message;
            }
          },
          error: (err) => {
            this.errorMessage = err.error.message || 'An error occurred';
          },
        });
    }
  }
  
}
