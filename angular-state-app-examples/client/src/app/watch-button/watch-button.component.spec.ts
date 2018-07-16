import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchButtonComponent } from './watch-button.component';

describe('WatchButtonComponent', () => {
  let component: WatchButtonComponent;
  let fixture: ComponentFixture<WatchButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WatchButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
