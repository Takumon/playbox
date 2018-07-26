import { TodoRoutingModule } from './todo-routing.module';

describe('TodoRoutingModule', () => {
  let todoRoutingModule: TodoRoutingModule;

  beforeEach(() => {
    todoRoutingModule = new TodoRoutingModule();
  });

  it('should create an instance', () => {
    expect(todoRoutingModule).toBeTruthy();
  });
});
