// src/app/components/partner-form/partner-form.component.ts
import { Component, OnInit }                     from '@angular/core';
import { CommonModule }                          from '@angular/common';
import { FormsModule }                           from '@angular/forms';
import { RouterModule, ActivatedRoute, Router }  from '@angular/router';

import { PartnerService } from '../../services/partner.service';
import { Partner }        from '../../models/partner';

@Component({
  selector: 'app-partner-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './partner-form.component.html'
})
export class PartnerFormComponent implements OnInit {
  partner: Partner = {
    name:        '',
    email:       '',
    phoneNumber: '',
    address:     '',
    website:     '',
    status:      'ACTIVE'
  };
  isEdit = false;

  constructor(
    private svc: PartnerService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.svc.getById(+id).subscribe((p: Partner) => {
        this.partner = p;
      });
    }
  }

  save() {
    if (this.isEdit && this.partner.id != null) {
      this.svc.update(this.partner.id, this.partner)
        .subscribe(() => this.router.navigate(['/partners']));
    } else {
      this.svc.create(this.partner)
        .subscribe(() => this.router.navigate(['/partners']));
    }
  }
}
