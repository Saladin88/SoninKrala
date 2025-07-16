import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalErrorViewComponent } from './global-error-view.component';

describe('GlobalErrorViewComponent', () => {
  let component: GlobalErrorViewComponent;
  let fixture: ComponentFixture<GlobalErrorViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GlobalErrorViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GlobalErrorViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
