import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowsSliderComponent } from './shows-slider.component';

describe('ShowsSliderComponent', () => {
  let component: ShowsSliderComponent;
  let fixture: ComponentFixture<ShowsSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowsSliderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowsSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
