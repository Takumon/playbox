import {
  async,
  ComponentFixture,
  TestBed,
  inject,
} from '@angular/core/testing';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/from';

import { PostsComponent } from './posts.component';
import { PostsService } from '../posts.service';



describe('PostsComponent', () => {
  let component: PostsComponent;
  let fixture: ComponentFixture<PostsComponent>;
  let el: HTMLElement;

  class PostsServiceMock {
    getAllPosts(): Observable<Array<any>> {
      const mockedPosts =  [
        {
          userId: 1,
          id: 1,
          title: 'sunt aut facere repellat provident occaecati excepturi optio reprehenderit',
          body: 'quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto'
        },
        {
          userId: 1,
          id: 2,
          title: 'qui est esse',
          body: 'est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla'
        }
      ];

      return Observable.from([mockedPosts]);
    }
  }


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostsComponent ],
    });

    TestBed.overrideComponent(PostsComponent, {
      set: {
        providers: [
          { provide: PostsService, useClass: PostsServiceMock },
        ]
      }
    })
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('オブジェクトが生成されるか', async(() => {
    fixture = TestBed.createComponent(PostsComponent);
    component = fixture.componentInstance;
    expect(component).toBeTruthy();
  }));

  it('postsを保持しているか', async(() => {
    fixture = TestBed.createComponent(PostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    expect(component.posts.length).toEqual(2);
    expect(component.posts[0]).toEqual({
          userId: 1,
          id: 1,
          title: 'sunt aut facere repellat provident occaecati excepturi optio reprehenderit',
          body: 'quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto'
      });

      expect(component.posts[1]).toEqual({
          userId: 1,
          id: 2,
          title: 'qui est esse',
          body: 'est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla'
      });
  }));


  it('postsのタイトルが描画されているか', async(() => {
    fixture = TestBed.createComponent(PostsComponent);
    fixture.detectChanges();
    el = fixture.debugElement.nativeElement;
    expect(el.querySelectorAll('h4')[0].textContent).toEqual('sunt aut facere repellat provident occaecati excepturi optio reprehenderit');
    expect(el.querySelectorAll('h4')[1].textContent).toEqual('qui est esse');
  }));
});
