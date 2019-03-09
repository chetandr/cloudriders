import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsortiumComponent } from './consortium.component';

describe('UserProfileComponent', () => {
  let component: ConsortiumComponent;
  let fixture: ComponentFixture<ConsortiumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsortiumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsortiumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
