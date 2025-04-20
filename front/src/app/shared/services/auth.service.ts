  import { HttpClient } from '@angular/common/http';
  import { Injectable } from '@angular/core';
  import { Observable } from 'rxjs';

  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {

    private apiUrlSignUp  = `http://localhost:3000/register`;
    private apiUrlSignIn = `http://localhost:3000/signIn`;

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
  }
