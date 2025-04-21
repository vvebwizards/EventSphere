import { Component, EventEmitter, Input, Output, ChangeDetectorRef } from '@angular/core';
import { Resource, ResourceService } from '../../services/resource.service';

@Component({
  selector: 'app-resource-card',
  standalone: false,
  templateUrl: './resource-card.component.html',
  styleUrls: ['./resource-card.component.css']
})
export class ResourceCardComponent {
  @Input() resource!: Resource;
  @Output() viewDetails = new EventEmitter<Resource>();
  @Output() modify = new EventEmitter<Resource>();
  @Output() delete = new EventEmitter<string>();
  showDeleteModal = false;

  constructor(
    private resourceService: ResourceService,
    private cdr: ChangeDetectorRef
  ) {}

  getTruncatedDescription(): string {
    return this.resource.description?.length > 100
      ? this.resource.description.slice(0, 100) + '...'
      : this.resource.description;
  }

  onViewDetails(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.getResourceById(this.resource.id, token).subscribe({
        next: (fullResource) => {
          this.viewDetails.emit(fullResource);
        },
        error: (err) => {
          console.error('Error fetching resource details:', err);
        }
      });
    } else {
      console.error('No access token found');
    }
  }

  onModify(): void {
    const token = localStorage.getItem('accessToken');
    if (token) {
      this.resourceService.getResourceById(this.resource.id, token).subscribe({
        next: (fullResource) => {
          this.modify.emit(fullResource);
        },
        error: (err) => {
          console.error('Error fetching resource for modify:', err);
        }
      });
    } else {
      console.error('No access token found');
    }
  }

  openDeleteModal(): void {
    console.log('Opening delete modal for resource:', this.resource.id);
    this.showDeleteModal = true;
    this.cdr.detectChanges();
  }

  closeDeleteModal(): void {
    console.log('Closing delete modal');
    this.showDeleteModal = false;
    this.cdr.detectChanges();
  }

  confirmDelete(): void {
    console.log('Confirming delete for resource:', this.resource.id);
    this.delete.emit(this.resource.id);
    this.closeDeleteModal();
  }
}