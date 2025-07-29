import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizListViewComponent } from './quiz-list-view.component';

describe('QuizListViewComponent', () => {
  let component: QuizListViewComponent;
  let fixture: ComponentFixture<QuizListViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuizListViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
