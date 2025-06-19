import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlphabetAudioViewComponent } from './alphabet-audio-view.component';

describe('AlphabetAudioViewComponent', () => {
  let component: AlphabetAudioViewComponent;
  let fixture: ComponentFixture<AlphabetAudioViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlphabetAudioViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlphabetAudioViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
