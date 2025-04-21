import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-progress-bar',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="progress-container">
      <div class="progress-text">{{ progressText }}</div>
      <div class="progress-bar">
        <div class="progress-fill" [style.width.%]="progress"></div>
      </div>
    </div>
  `,
  styles: [`
    .progress-container {
      margin-bottom: var(--spacing-4);
    }
    
    .progress-text {
      font-size: 14px;
      color: var(--neutral-600);
      margin-bottom: var(--spacing-2);
      text-align: right;
    }
    
    .progress-bar {
      height: 6px;
      background-color: var(--neutral-200);
      border-radius: 3px;
      overflow: hidden;
    }
    
    .progress-fill {
      height: 100%;
      background-color: var(--primary-color);
      transition: width 0.3s ease;
    }
  `]
})
export class ProgressBarComponent {
  @Input() progress: number = 0;
  
  get progressText(): string {
    return `${this.progress}% Complete`;
  }
}