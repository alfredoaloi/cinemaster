import { TestBed } from '@angular/core/testing';

import { PersonalAreaService } from './personal-area.service';

describe('PersonalAreaService', () => {
  let service: PersonalAreaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonalAreaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
