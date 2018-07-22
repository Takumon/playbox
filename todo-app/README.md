# Angular Todo app with NgRx


## なにこれ
簡単なTodoアプリ作成を通し、Angularで状態管理する方法を学ぶためのチュートリアルです。

## Todoアプリのイメージ



## 使っているフレームワーク,ライブラリ
|名前|説明|
|---|---|
|[Angular](https://angular.io/)| Googleが提供している全部入りSPAフレームワーク|
|[NgRx](http://ngrx.github.io/)|Angularの状態管理を行うためのライブラリ|

## 前提条件
* Node.jsインストール済み
* [Angular CLI](https://cli.angular.io/)グローバルインストール済み

```terminal
$ npm i -g @angular/cli
```

## 作成手順
### 1. 単純なAngularアプリをAngular CLIで作成

* 下記コマンドでアプリの雛形を作成
  * オプション
    * `--style=sass`
      * スタイルシートはSassに指定(単にSassが好きだからです)
    * `--routing`
      * ルーティングを行うのであらかじめボイライープレートを生成しておく

```terminal
$ ng new todo-app --style=sass --routing
```

* アプリを起動

```terminal
$ cd todo-app
$ ng serve -o
```


### 2. Todo機能を実装

* モデルを生成

```Terminal
$ ng g class models/todo
```

* 下記のように修正

```models/todo.ts
export class Todo {
   id: number;
   title = '';
   complete = false;

   constructor(values: Object = {}) {
    Object.assign(this, values);
   }
}
```

* サービスを生成

```Terminal
$ ng g service services/TodoData
```


### 2. NgRxを追加

* NgRxのスキャフォールド生成できるように下記を実行

```terminal
$ npm i -D @ngrx/schematics
$ ng config cli.defaultCollection @ngrx/schematics
```


* NgRxのライブラリ群をインストール

```terminal
$ npm i -s @ngrx/{store,effects,entity,store-devtools}
```




This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.0.8.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
