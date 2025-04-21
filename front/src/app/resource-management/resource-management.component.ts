import { Component } from '@angular/core';
import { ResourceService, Resource } from '../shared/services/resource.service';

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
  isEditMode: boolean = false;
  selectedResource: Resource | null = null;
  selectedFile: File | null = null;
  constructor(private resourceService: ResourceService) {}

  ngOnInit(): void {
    this.loadResources();
  }

  loadResources(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.getMyResources(token).subscribe({
        next: (data) => {
          this.resources = data;
          this.filteredResources = data;
        },
        error: (err) => {
          console.error('Error loading resources:', err);
          alert('Failed to load resources.');
        }
      });
    } else {
      console.warn('No access token found!');
      alert('Please log in to view resources.');
    }
  }

  filterResources(): void {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredResources = this.resources.filter((resource) =>
      resource.name.toLowerCase().includes(term)
    );
  }

  onAddNewResource(): void {
    this.isEditMode = false;
    this.selectedResource = null;
    this.showAddModal = true;
  }

  onModifyResource(resource: Resource): void {
    console.log('Modify event received for resource:', resource.id);
    this.isEditMode = true;
    this.selectedResource = { ...resource };
    this.showAddModal = true;
  }

  onDeleteResource(resourceId: string): void {
    console.log('Delete event received for resource:', resourceId);
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.deleteResource(resourceId, token).subscribe({
        next: () => {
          this.resources = this.resources.filter((r) => r.id !== resourceId);
          this.filteredResources = [...this.resources];
          alert('Resource deleted successfully!');
        },
        error: (err) => {
          console.error('Error deleting resource:', err);
          alert('Failed to delete resource.');
        }
      });
    } else {
      console.warn('No access token found!');
      alert('You must be logged in to delete a resource.');
    }
  }

  closeAddModal(): void {
    this.showAddModal = false;
    this.isEditMode = false;
    this.selectedResource = null;
  }

  saveNewResource(resource: Resource): void {
    const token = localStorage.getItem('accessToken') || '';
    if (this.isEditMode && this.selectedResource) {
      this.resourceService.updateResource(
        this.selectedResource.id, 
        resource, 
        this.selectedFile, 
        token
      ).subscribe({
        next: (updatedResource) => {
          const index = this.resources.findIndex((r) => r.id === updatedResource.id);
          if (index !== -1) {
            this.resources[index] = updatedResource;
            this.filteredResources = [...this.resources];
          }
          this.showAddModal = false;
          this.isEditMode = false;
          this.selectedResource = null;
          this.selectedFile = null;
          alert('Resource updated successfully!');
        },
        error: (err) => {
          console.error('Error updating resource:', err);
          alert('Failed to update resource.');
        }
      });
    } else {
      this.resourceService.createResource(
        resource, 
        this.selectedFile, 
        token
      ).subscribe({
        next: (newResource) => {
          this.resources.push(newResource);
          this.filteredResources = [...this.resources];
          this.showAddModal = false;
          this.selectedFile = null;
          alert('Resource created successfully!');
        },
        error: (err) => {
          console.error('Error creating resource:', err);
          alert('Failed to create resource.');
        }
      });
    }
  }
  handleSave(event: {resource: Resource, file: File | null}): void {
    this.selectedFile = event.file;
    this.saveNewResource(event.resource);
  }
}