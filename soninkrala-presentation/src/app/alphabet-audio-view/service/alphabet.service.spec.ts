import { TestBed } from '@angular/core/testing';

import { AlphabetService } from './alphabet.service';

describe('AlphabetService', () => {
  let service: AlphabetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlphabetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
