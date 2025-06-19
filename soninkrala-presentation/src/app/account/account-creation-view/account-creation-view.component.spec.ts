import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountCreationViewComponent } from './account-creation-view.component';

describe('AccountCreationViewComponent', () => {
  let component: AccountCreationViewComponent;
  let fixture: ComponentFixture<AccountCreationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountCreationViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountCreationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
