import { Component } from '@angular/core';
import { Resource, ResourceService } from '../shared/services/resource.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-resource-details',
  standalone: false,
  templateUrl: './resource-details.component.html',
  styleUrl: './resource-details.component.css'
})
export class ResourceDetailsComponent {
  resource: Resource | null = null;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private resourceService: ResourceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const resourceId = this.route.snapshot.paramMap.get('id');
    if (resourceId) {
      this.getDetails(resourceId);
    } else {
      this.error = 'No resource ID provided.';
    }
  }

  getDetails(resourceId: string): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.getResourceById(resourceId, token).subscribe({
        next: (fullResource) => {
          this.resource = fullResource;
        },
        error: (err) => {
          console.error('Error fetching resource details:', err);
          this.error = 'Failed to load resource details.';
        }
      });
    } else {
      console.error('No access token found');
      this.error = 'You must be logged in to view resource details.';
    }
  }

}
