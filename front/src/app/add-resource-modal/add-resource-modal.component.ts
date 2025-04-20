import { Component, EventEmitter, Output } from '@angular/core';
import { Resource } from '../shared/services/resource.service';

@Component({
  selector: 'app-add-resource-modal',
  standalone: false,
  templateUrl: './add-resource-modal.component.html',
  styleUrl: './add-resource-modal.component.css'
})
export class AddResourceModalComponent {
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<Resource>();

  newResource: Resource = {
    id: '',
    name: '',
    type: '',
    description: '',
    available: true,
    costPerHour: 0,
    location: '',
    lastBookedDate: '',
    dynamicPricePerHour: 0,
    ownerId: ''
  };

  onClose(): void {
    this.close.emit();
  }

  onSave(): void {
    this.save.emit(this.newResource);
    this.resetForm();
  }

  private resetForm(): void {
    this.newResource = {
      id: '',
      name: '',
      type: '',
      description: '',
      available: true,
      costPerHour: 0,
      location: '',
      lastBookedDate: '',
      dynamicPricePerHour: 0,
      ownerId: ''
    };
  }
}