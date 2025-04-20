import { Component } from '@angular/core';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-user-profile',
  standalone: false,
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {
  user: any = null;
  loading = true;
  error: string | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.authService.getCurrentUser(token).subscribe({
        next: (data) => {
          this.user = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load profile';
          this.loading = false;
        },
      });
    } else {
      this.error = 'No token found';
      this.loading = false;
    }
  }
}
