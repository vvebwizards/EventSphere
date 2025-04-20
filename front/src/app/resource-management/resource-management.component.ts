import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-resource-management',
  standalone: false,
  templateUrl: './resource-management.component.html',
  styleUrl: './resource-management.component.css'
})
export class ResourceManagementComponent {
  @Output() addNewResource = new EventEmitter<void>();
  searchTerm: string = '';

  resources = [
    {
      imageUrl: 'https://via.placeholder.com/300x150',
      name: 'Resource 1',
      type: 'Document',
      description: 'This is a detailed description of Resource 1 that might be quite long and needs truncation if it exceeds the character limit.'
    },
    {
      imageUrl: 'https://via.placeholder.com/300x150',
      name: 'Resource 2',
      type: 'Video',
      description: 'A short description for Resource 2.'
    },
    {
      imageUrl: 'https://via.placeholder.com/300x150',
      name: 'Resource 3',
      type: 'Image',
      description: 'This is Resource 3 with a medium-length description that fits well.'
    }
  ];

  filteredResources = [...this.resources];

  filterResources() {
    this.filteredResources = this.resources.filter(resource =>
      resource.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  onAddNewResource() {
    this.addNewResource.emit();
  }

  handleViewDetails(resource: any) {
    console.log('View details for:', resource.name);
  }

  handleModify(resource: any) {
    console.log('Modify:', resource.name);
  }

  handleDelete(resource: any) {
    console.log('Delete:', resource.name);
    this.resources = this.resources.filter(r => r !== resource);
    this.filterResources();
  }
}
