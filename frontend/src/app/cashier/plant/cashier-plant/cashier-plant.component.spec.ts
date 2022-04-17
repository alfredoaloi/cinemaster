import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CashierPlantComponent } from './cashier-plant.component';

describe('CashierPlantComponent', () => {
  let component: CashierPlantComponent;
  let fixture: ComponentFixture<CashierPlantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CashierPlantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CashierPlantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
