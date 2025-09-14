import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermAgrementViewComponentComponent } from './term-agrement-view-component.component';

describe('TermAgrementViewComponentComponent', () => {
  let component: TermAgrementViewComponentComponent;
  let fixture: ComponentFixture<TermAgrementViewComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermAgrementViewComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TermAgrementViewComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
