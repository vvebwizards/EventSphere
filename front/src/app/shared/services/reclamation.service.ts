import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Reclamation {
  idRec: number;
  ownerId: string;
  title: string;
  description: string;
  date: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReclamationService {
  private baseUrl = 'http://localhost:8084/reclamation-api/reclamation';
  constructor(private http: HttpClient) { }

  getMyReclamations(token: string): Observable<Reclamation[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Reclamation[]>(`${this.baseUrl}/get-all-reclamations`, { headers });
  }
  
  deleteReclamation(id: number, token: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/Delete/${id}`, { headers });
  }
  
  getReclamationById(id: number, token: string): Observable<Reclamation> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.get<Reclamation>(`${this.baseUrl}/getOne/${id}`, { headers });
  }

  updateReclamation(id: number, reclamation: Reclamation, token: string): Observable<Reclamation> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.put<Reclamation>(`${this.baseUrl}/updateOne/${id}`, reclamation, { headers });
  }
  
  createReclamation(reclamation: Reclamation, token: string): Observable<Reclamation> {
    if (!token) {
      throw new Error('Token is required for authentication');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<Reclamation>(`${this.baseUrl}/addOne`, reclamation, { headers });
  }
}