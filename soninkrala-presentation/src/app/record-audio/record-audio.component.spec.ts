import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordAudioComponent } from './record-audio.component';

describe('RecordAudioComponent', () => {
  let component: RecordAudioComponent;
  let fixture: ComponentFixture<RecordAudioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecordAudioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecordAudioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
