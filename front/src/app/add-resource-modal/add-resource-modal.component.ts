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
  @Output() save = new EventEmitter<{resource: Resource, file: File | null}>();
  
  selectedFile: File | null = null;
  newResource: Resource = {
    id: '',
    name: '',
    type: '',
    description: '',
    available: true,
    costPerHour: 0,
    location: '',
 
    dynamicPricePerHour: 0,
    ownerId: '',
    imagePath: null
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
    this.save.emit({
      resource: this.newResource,
      file: this.selectedFile
    });
    this.resetForm();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
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
 
      dynamicPricePerHour: 0,
      ownerId: '',
      imagePath: null
    };
    this.selectedFile = null;
  }
}