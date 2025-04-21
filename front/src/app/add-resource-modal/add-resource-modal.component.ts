import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { Resource } from '../shared/services/resource.service';

@Component({
  selector: 'app-add-resource-modal',
  standalone: false,
  templateUrl: './add-resource-modal.component.html',
  styleUrls: ['./add-resource-modal.component.css']
})
export class AddResourceModalComponent implements OnInit {
  @Input() resource: Resource | null = null;
  @Input() isEditMode: boolean = false;
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

  ngOnInit(): void {
    if (this.isEditMode && this.resource) {
      this.newResource = { ...this.resource };
    }
  }

  onClose(): void {
    this.close.emit();
    this.resetForm();
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