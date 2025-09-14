import { TestBed } from '@angular/core/testing';

import { ReferentialDataService } from './referential-data.service';

describe('ReferentialDataService', () => {
  let service: ReferentialDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReferentialDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
