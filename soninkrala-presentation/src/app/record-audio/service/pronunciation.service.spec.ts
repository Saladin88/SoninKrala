import { TestBed } from '@angular/core/testing';

import { PronunciationService } from './pronunciation.service';

describe('PronunciationService', () => {
  let service: PronunciationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PronunciationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
