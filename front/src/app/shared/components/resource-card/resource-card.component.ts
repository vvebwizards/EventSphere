import { Component, EventEmitter, Input, Output, ChangeDetectorRef } from '@angular/core';
import { Resource, ResourceService } from '../../services/resource.service';
import { Router } from '@angular/router';

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
 

  constructor(
    private resourceService: ResourceService,
    private router: Router
  ) {}

  getTruncatedDescription(): string {
    return this.resource.description?.length > 100
      ? this.resource.description.slice(0, 100) + '...'
      : this.resource.description;
  }

  onViewDetails(): void {
    this.router.navigate(['/resourceDetails', this.resource.id]);
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

 

  
}