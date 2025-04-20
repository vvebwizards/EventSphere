import { Component } from '@angular/core';
import { ResourceService, Resource } from '../shared/services/resource.service';
import { AuthService } from '../shared/services/auth.service'; // Hypothetical AuthService

@Component({
  selector: 'app-resource-management',
  standalone: false,
  templateUrl: './resource-management.component.html',
  styleUrls: ['./resource-management.component.css']
})
export class ResourceManagementComponent {
  resources: Resource[] = [];
  filteredResources: Resource[] = [];
  searchTerm: string = '';
  showAddModal: boolean = false;

  constructor(
    private resourceService: ResourceService,
    private authService: AuthService 
  ) {}

  ngOnInit(): void {
    this.loadResources();
  }

  loadResources(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.getMyResources(token).subscribe((data) => {
        this.resources = data;
        this.filteredResources = data;
      });
    } else {
      console.warn('No access token found!');
    }
  }
  

  filterResources(): void {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredResources = this.resources.filter((resource) =>
      resource.name.toLowerCase().includes(term)
    );
  }

  onAddNewResource(): void {
    this.showAddModal = true;
  }

  closeAddModal(): void {
    this.showAddModal = false;
  }

    saveNewResource(resource: Resource): void {
      const token = localStorage.getItem('accessToken') || ''; 
      this.resourceService.createResource(resource, token).subscribe({
        next: (newResource) => {
          this.resources.push(newResource);
          this.filteredResources = [...this.resources];
          this.showAddModal = false;
        },
        error: (err) => {
          console.error('Error creating resource:', err);
        
        }
      });
    }

}