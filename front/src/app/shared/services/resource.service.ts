import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Resource {
  id: string;
  name: string;
  type: string;
  description: string;
  available: boolean;
  costPerHour: number;
  location: string;
  lastBookedDate: string;
  dynamicPricePerHour: number;
  ownerId: string;
}

@Injectable({
  providedIn: 'root'
})
export class ResourceService {
  private baseUrl = 'http://localhost:8087/resource-api/resources';
  constructor(private http: HttpClient) { }
  getMyResources(token: string): Observable<Resource[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Resource[]>(`${this.baseUrl}/my-resources`, { headers });
  }
  
  deleteResource(id: string, token: string): Observable<void> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(`${this.baseUrl}/Delete/${id}`, { headers });
  }
  
  getResourceById(id: string, token: string): Observable<Resource> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.get<Resource>(`${this.baseUrl}/getOne/${id}`,{ headers });
  }

  updateResource(id: string, resource: Resource, token: string): Observable<Resource> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.put<Resource>(`${this.baseUrl}/updateOne/${id}`, resource, { headers });
  }
  
  createResource(resource: Resource, token: string): Observable<Resource> {
    if (!token) {
      throw new Error('Token is required for authentication');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<Resource>(`${this.baseUrl}/addOne`, resource, { headers });
  }
  
}