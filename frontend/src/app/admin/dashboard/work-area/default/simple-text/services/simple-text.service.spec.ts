import { TestBed } from '@angular/core/testing';

import { SimpleTextService } from './simple-text.service';

describe('SimpleTextService', () => {
  let service: SimpleTextService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SimpleTextService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
