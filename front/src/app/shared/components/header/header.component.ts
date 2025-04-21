// header.component.ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  firstName: string = '';
  lastName: string = '';
  userRole: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((status) => {
      this.isLoggedIn = status;
      if (status) {
        const token = localStorage.getItem('accessToken');
        if (token) {
          this.authService.getCurrentUser(token).subscribe((userData) => {
            this.firstName = userData.firstName;
            this.lastName = userData.lastName;
            this.userRole = userData.role;
          });
        }
      } else {
        this.firstName = '';
        this.lastName = '';
        this.userRole = '';
      }
    });
  }
  

  logout(): void {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
      console.error('No refresh token found');
      return;
    }
  
    this.authService.logout(refreshToken).subscribe({
      next: () => {
        this.router.navigate(['/home']);
        this.authService.setLoginStatus(false); 
      },
      error: (err) => {
        console.error('Logout failed:', err);
      },
    });
  }
  
}
