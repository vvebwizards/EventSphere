  import { HttpClient, HttpHeaders } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { BehaviorSubject, Observable } from 'rxjs';

  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {

    private apiUrlSignUp  = `http://localhost:3000/register`;
    private apiUrlSignIn = `http://localhost:3000/signIn`;
    private apiUrlGetUser = 'http://localhost:3000/user/getMe';
    private logoutUrl = 'http://localhost:3000/user/logout';

    constructor(private http: HttpClient) {}

    signup(user: {
      firstName: string;
      lastName: string;
      email: string;
      password: string;
      role: string;
    }): Observable<any> {
      return this.http.post(this.apiUrlSignUp , user);
    }
    login(email: string, password: string): Observable<any> {
      return this.http.post(this.apiUrlSignIn, { email, password });
    }
    getCurrentUser(token: string): Observable<any> {
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
      return this.http.get<any>(this.apiUrlGetUser, { headers });
  }

  logout(refreshToken: string) {
    return this.http.post(this.logoutUrl, { refreshToken });
  }
  private isLoggedInSubject = new BehaviorSubject<boolean>(!!localStorage.getItem('accessToken'));
isLoggedIn$ = this.isLoggedInSubject.asObservable();

setLoginStatus(status: boolean): void {
  this.isLoggedInSubject.next(status);
}
  }
