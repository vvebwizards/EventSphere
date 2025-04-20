import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-resource-card',
  standalone: false,
  templateUrl: './resource-card.component.html',
  styleUrl: './resource-card.component.css'
})
export class ResourceCardComponent {
  @Input() resource: { imageUrl: string, name: string, type: string, description: string } = {
    imageUrl: '',
    name: '',
    type: '',
    description: ''
  };
  @Output() viewDetails = new EventEmitter<void>();
  @Output() modify = new EventEmitter<void>();
  @Output() delete = new EventEmitter<void>();

  getTruncatedDescription(): string {
    const maxLength = 100;
    if (this.resource.description.length > maxLength) {
      return this.resource.description.slice(0, maxLength) + '...';
    }
    return this.resource.description;
  }

  onViewDetails() {
    this.viewDetails.emit();
  }

  onModify() {
    this.modify.emit();
  }

  onDelete() {
    this.delete.emit();
  }
}
