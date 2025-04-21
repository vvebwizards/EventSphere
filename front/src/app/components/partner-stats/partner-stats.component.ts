import { Component, OnInit } from '@angular/core';
import { PartnerService } from '../../services/partner.service';

@Component({
  selector: 'app-partner-stats',
  template: `
    <h2>Partner Statistics</h2>
    <ul>
      <li *ngFor="let key of keys">
        {{key}}: {{stats[key]}}
      </li>
    </ul>
    <button routerLink="/partners">← Back</button>
  `
})
export class PartnerStatsComponent implements OnInit {
  stats: { [status: string]: number } = {};
  keys: string[] = [];

  constructor(private svc: PartnerService) {}

  ngOnInit() {
    this.svc.stats().subscribe(data => {
      this.stats = data;
      this.keys = Object.keys(data);
    });
  }
}
