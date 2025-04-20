import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:3000/register'; 

  constructor(private http: HttpClient) {}

  signup(user: {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    role: string;
  }): Observable<any> {
    return this.http.post(this.apiUrl, user);
  }
}