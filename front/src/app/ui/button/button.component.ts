import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingSpinnerComponent } from '../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule, LoadingSpinnerComponent],
  template: `
    <button 
      [ngClass]="['btn', 'btn-' + type]" 
      [disabled]="disabled" 
      (click)="onClick.emit()">
      <div class="btn-content">
        <app-loading-spinner *ngIf="showSpinner"></app-loading-spinner>
        <span>{{ label }}</span>
      </div>
    </button>
  `,
  styles: [`
    .btn {
      display: inline-flex;
      justify-content: center;
      align-items: center;
      padding: 12px 24px;
      border-radius: var(--border-radius-md);
      font-weight: 500;
      font-size: 16px;
      cursor: pointer;
      transition: all 0.2s ease;
      border: none;
      width: 100%;
    }
    
    .btn-content {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
    }
    
    .btn-primary {
      background-color: var(--primary-color);
      color: white;
    }
    
    .btn-primary:hover:not(:disabled) {
      background-color: var(--primary-dark);
      transform: translateY(-1px);
    }
    
    .btn-primary:active:not(:disabled) {
      transform: translateY(0);
    }
    
    .btn-secondary {
      background-color: var(--neutral-200);
      color: var(--neutral-800);
    }
    
    .btn-secondary:hover:not(:disabled) {
      background-color: var(--neutral-300);
      transform: translateY(-1px);
    }
    
    .btn:disabled {
      opacity: 0.7;
      cursor: not-allowed;
    }
  `]
})
export class ButtonComponent {
  @Input() label: string = 'Button';
  @Input() type: 'primary' | 'secondary' = 'primary';
  @Input() disabled: boolean = false;
  @Input() showSpinner: boolean = false;
  @Output() onClick = new EventEmitter<void>();
}