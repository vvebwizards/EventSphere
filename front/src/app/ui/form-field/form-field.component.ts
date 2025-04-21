import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-field',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="form-field">
      <label [class.required]="required">
        {{ label }}
      </label>
      <div class="field-content">
        <ng-content></ng-content>
      </div>
      <div *ngIf="error" class="error-message">
        {{ error }}
      </div>
    </div>
  `,
  styles: [`
    .form-field {
      margin-bottom: var(--spacing-3);
    }
    
    label {
      display: block;
      margin-bottom: var(--spacing-2);
      font-weight: 500;
      color: var(--neutral-800);
      font-size: 14px;
    }
    
    .required::after {
      content: '*';
      color: var(--error-color);
      margin-left: 4px;
    }
    
    .field-content {
      position: relative;
    }
    
    .field-content ::ng-deep input,
    .field-content ::ng-deep select {
      width: 100%;
      padding: 12px 16px;
      border: 1px solid var(--neutral-300);
      border-radius: var(--border-radius-md);
      font-size: 16px;
      transition: border-color 0.2s, box-shadow 0.2s;
    }
    
    .field-content ::ng-deep input:focus,
    .field-content ::ng-deep select:focus {
      outline: none;
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(0, 122, 255, 0.2);
    }
    
    .field-content ::ng-deep input.invalid,
    .field-content ::ng-deep select.invalid {
      border-color: var(--error-color);
    }
    
    .field-content ::ng-deep input.invalid:focus,
    .field-content ::ng-deep select.invalid:focus {
      box-shadow: 0 0 0 3px rgba(255, 59, 48, 0.2);
    }
    
    .error-message {
      font-size: 12px;
      color: var(--error-color);
      margin-top: 4px;
      min-height: 18px;
      animation: fadeIn 0.2s ease-in;
    }
    
    @keyframes fadeIn {
      from { opacity: 0; }
      to { opacity: 1; }
    }
  `]
})
export class FormFieldComponent {
  @Input() label: string = '';
  @Input() required: boolean = false;
  @Input() error: string | null = null;
}