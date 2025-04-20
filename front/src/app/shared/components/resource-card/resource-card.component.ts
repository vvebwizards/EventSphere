import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Resource, ResourceService } from '../../services/resource.service';


@Component({
  selector: 'app-resource-card',
  standalone: false,
  templateUrl: './resource-card.component.html',
  styleUrl: './resource-card.component.css'
})
export class ResourceCardComponent {
  @Input() resource!: Resource;

  @Output() viewDetails = new EventEmitter<Resource>();
  @Output() modify = new EventEmitter<Resource>();
  @Output() delete = new EventEmitter<string>();

  constructor(private resourceService: ResourceService) {}

  getTruncatedDescription(): string {
    return this.resource.description?.length > 100
      ? this.resource.description.slice(0, 100) + '...'
      : this.resource.description;
  }

  onViewDetails(): void {
  
    this.resourceService.getResourceById(this.resource.id).subscribe((fullResource) => {
      this.viewDetails.emit(fullResource);
    });
  }

  onModify(): void {
    this.resourceService.getResourceById(this.resource.id).subscribe((fullResource) => {
      this.modify.emit(fullResource);
    });
  }

  onDelete(): void {
    this.delete.emit(this.resource.id);
  }
}