import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn: boolean = false;
  firstName: string = '';
  lastName: string = '';
  userRole: string = '';
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.checkLoginStatus();
  }

  checkLoginStatus(): void {
    const token = localStorage.getItem('accessToken');
  
    if (token) {
      this.isLoggedIn = true;
      this.authService.getCurrentUser(token).subscribe(
        (userData) => {
          if (userData) {
            this.firstName = userData.firstName;
            this.lastName = userData.lastName;
            this.userRole = userData.role;
          }
        },
        (error) => {
          console.error('Error fetching user info:', error);
          this.isLoggedIn = false;
        }
      );
    } else {
      this.isLoggedIn = false;  
    }
  }

  logout(): void {
    // this.authService.logout(); 
    // this.isLoggedIn = false;
  }
}
