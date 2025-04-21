// src/app/services/partner.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Partner } from '../models/partner';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PartnerService {
  private base = `http://localhost:8083/partnership/api/partners`;

  constructor(private http: HttpClient) {}

  list(): Observable<Partner[]> {
    return this.http.get<Partner[]>(this.base);
  }
  get(id: number): Observable<Partner> {
    return this.http.get<Partner>(`${this.base}/${id}`);
  }
  create(p: Partner): Observable<Partner> {
    return this.http.post<Partner>(this.base, p);
  }
  update(id: number, p: Partner): Observable<Partner> {
    return this.http.put<Partner>(`${this.base}/${id}`, p);
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
  search(name?: string, status?: string): Observable<Partner[]> {
    let params = new HttpParams();
    if (name)   params = params.set('name', name);
    if (status) params = params.set('status', status);
    return this.http.get<Partner[]>(`${this.base}/search`, { params });
  }
  stats(): Observable<{[status: string]: number}> {
    return this.http.get<{[status: string]: number}>(`${this.base}/statistics`);
  }
  getById(id: number): Observable<Partner> {
    return this.http.get<Partner>(`${this.base}/${id}`);
  }
}
