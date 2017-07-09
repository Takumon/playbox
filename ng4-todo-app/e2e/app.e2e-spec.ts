import { Ng4TodoAppPage } from './app.po';

describe('ng4-todo-app App', () => {
  let page: Ng4TodoAppPage;

  beforeEach(() => {
    page = new Ng4TodoAppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
