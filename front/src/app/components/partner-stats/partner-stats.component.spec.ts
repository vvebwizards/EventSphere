import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartnerStatsComponent } from './partner-stats.component';

describe('PartnerStatsComponent', () => {
  let component: PartnerStatsComponent;
  let fixture: ComponentFixture<PartnerStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PartnerStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartnerStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
