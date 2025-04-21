import { Component, OnInit } from '@angular/core';
import { CommonModule }     from '@angular/common';
import { PartnerService }   from '../../services/partner.service';

@Component({
  selector: 'app-partner-stats',
  standalone: true,
  imports: [ CommonModule ],
  templateUrl: './partner-stats.component.html'
})
export class PartnerStatsComponent implements OnInit {
  stats: { status: string; count: number }[] = [];

  constructor(private svc: PartnerService) {}

  ngOnInit() {
    this.svc.getStatistics().subscribe(data => {
      // transform { ACTIVE: 3, INACTIVE: 2 } → [ {status:'ACTIVE',count:3}, ... ]
      this.stats = Object.entries(data).map(([status, count]) => ({ status, count }));
    });
  }
}
