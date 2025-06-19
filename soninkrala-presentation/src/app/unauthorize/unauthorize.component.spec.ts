import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnauthorizeComponent } from './unauthorize.component';

describe('UnauthorizeComponent', () => {
  let component: UnauthorizeComponent;
  let fixture: ComponentFixture<UnauthorizeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnauthorizeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnauthorizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
