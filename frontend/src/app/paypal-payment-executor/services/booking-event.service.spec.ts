import { TestBed } from '@angular/core/testing';

import { BookingEventService } from './booking-event.service';

describe('BookingEventService', () => {
  let service: BookingEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookingEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
