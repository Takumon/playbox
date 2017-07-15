import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Component } from '@angular/core';

import { AppComponent } from './app.component';


@Component({
  selector: 'app-dummy',
  template: ''
})
class DummyComponent {
}

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
         { path: '', component: DummyComponent }
        ])
      ],
      declarations: [
        AppComponent,
        DummyComponent,
      ],
    }).compileComponents();
  }));

  it('オブジェクトが生成されているか', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));

  it(`タイトルがあっているか`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('Angular + Express with TypeScript');
  }));

  it('h1タグ中にタイトルがあるか', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Angular + Express with TypeScript');
  }));
});
