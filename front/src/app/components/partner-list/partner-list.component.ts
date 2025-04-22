// src/app/components/partner-list/partner-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule }      from '@angular/common';
import { FormsModule }       from '@angular/forms';
import { RouterModule }      from '@angular/router';
import { PartnerService }    from '../../services/partner.service';
import { Partner }           from '../../models/partner';

@Component({
  selector: 'app-partner-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './partner-list.component.html'
})
export class PartnerListComponent implements OnInit {
  partners: Partner[] = [];
  filterName   = '';
  filterStatus = '';

  constructor(private svc: PartnerService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.svc.search(this.filterName, this.filterStatus)
      .subscribe(list => this.partners = list);
  }

  delete(id: number) {
    this.svc.delete(id).subscribe(() => this.load());
  }
}
