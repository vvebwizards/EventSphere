import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  // private authService = inject(AuthService);
  
  // isScrolled = false;
  // mobileMenuOpen = false;
  // userMenuOpen = false;
  // currentUser: User | null = null;
  
  // constructor() {
  //   this.authService.currentUser$.subscribe(user => {
  //     this.currentUser = user;
  //   });
  // }
  
  // @HostListener('window:scroll')
  // onWindowScroll() {
  //   this.isScrolled = window.scrollY > 10;
  // }
  
  // @HostListener('document:click', ['$event'])
  // onDocumentClick(event: MouseEvent) {
  //   const target = event.target as HTMLElement;
  //   if (!target.closest('.user-menu')) {
  //     this.userMenuOpen = false;
  //   }
  // }
  
  // toggleMobileMenu() {
  //   this.mobileMenuOpen = !this.mobileMenuOpen;
  //   if (this.mobileMenuOpen) {
  //     document.body.style.overflow = 'hidden';
  //   } else {
  //     document.body.style.overflow = '';
  //   }
  // }
  
  // closeMobileMenu() {
  //   this.mobileMenuOpen = false;
  //   document.body.style.overflow = '';
  // }
  
  // toggleUserMenu() {
  //   this.userMenuOpen = !this.userMenuOpen;
  // }
  
  // logout() {
  //   this.authService.logout();
  //   this.userMenuOpen = false;
  // }
}
