import { Component, OnInit } from '@angular/core';
import { ReclamationService } from '../shared/services/reclamation.service';
import { Reclamation } from '../shared/services/reclamation.service';

@Component({
  selector: 'app-reclamations',
  standalone: false,
  templateUrl: './reclamations.component.html',
  styleUrl: './reclamations.component.css'
})
export class ReclamationsComponent implements OnInit {
  reclamations: Reclamation[] = [];
  errorMessage: string | null = null;

  constructor(private reclamationService: ReclamationService) {}

  ngOnInit(): void {
    // Assuming token is stored in localStorage; adjust as needed
    const token = localStorage.getItem('accessToken') || '';
    if (token) {
      this.reclamationService.getMyReclamations(token).subscribe({
        next: (data: Reclamation[]) => {
          this.reclamations = data;
        },
        error: (error) => {
          this.errorMessage = 'Failed to load reclamations. Please try again later.';
          console.error('Error fetching reclamations:', error);
        }
      });
    } else {
      this.errorMessage = 'Authentication token not found. Please log in.';
    }
  }
}