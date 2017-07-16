import { TestBed, async, inject } from '@angular/core/testing';
import {HttpModule, BaseRequestOptions, Http, Response, ResponseOptions} from '@angular/http';
import {MockBackend, MockConnection} from '@angular/http/testing';

import { PostsService } from './posts.service';

describe('PostsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [PostsService, {
        provide: Http,
        useFactory: (backend, options) => new Http(backend, options),
        deps: [MockBackend, BaseRequestOptions]
      }, MockBackend, BaseRequestOptions]
    });
  });

  it('オブジェクトが生成されるか', async(inject([MockBackend, PostsService], (backend: MockBackend , service: PostsService) => {
    expect(service).toBeTruthy();
  })));

  it('getAllPosts_Portsが取得できるか', async(inject([MockBackend, PostsService], (backend: MockBackend , service: PostsService) => {
    backend.connections.subscribe((conn: MockConnection) => {
      const mockedPosts = [
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
      
      const ops = new ResponseOptions({body: mockedPosts});

      conn.mockRespond(new Response(ops));
    });

    service.getAllPosts().subscribe(posts => {
      expect(posts.length).toEqual(2);
      expect(posts[0]).toEqual({
          userId: 1,
          id: 1,
          title: 'sunt aut facere repellat provident occaecati excepturi optio reprehenderit',
          body: 'quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto'
      });

      expect(posts[1]).toEqual({
          userId: 1,
          id: 2,
          title: 'qui est esse',
          body: 'est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla'
      });
    });
  })));


});
