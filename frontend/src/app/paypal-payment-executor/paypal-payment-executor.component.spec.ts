import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalPaymentExecutorComponent } from './paypal-payment-executor.component';

describe('PaypalPaymentExecutorComponent', () => {
  let component: PaypalPaymentExecutorComponent;
  let fixture: ComponentFixture<PaypalPaymentExecutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaypalPaymentExecutorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalPaymentExecutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
