import { Ng4MeanPage } from './app.po';

describe('ng4-mean App', () => {
  let page: Ng4MeanPage;

  beforeEach(() => {
    page = new Ng4MeanPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
