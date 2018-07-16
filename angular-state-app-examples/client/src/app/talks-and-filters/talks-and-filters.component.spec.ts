import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TalksAndFiltersComponent } from './talks-and-filters.component';

describe('TalksAndFiltersComponent', () => {
  let component: TalksAndFiltersComponent;
  let fixture: ComponentFixture<TalksAndFiltersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TalksAndFiltersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TalksAndFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
