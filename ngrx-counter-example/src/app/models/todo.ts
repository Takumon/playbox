export class Todo {
  constructor(
    public id?: string,
    public text?: string,
    public checked?: boolean,
    public createAt?: number,
    public upadtedAt?: number
  ) {}
}
