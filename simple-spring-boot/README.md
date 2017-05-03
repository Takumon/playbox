# SpringBootを使った簡単な従業員登録アプリ
[playbox/simple-spring-boot](https://github.com/Takumon/playbox/edit/master/simple-spring-app)をもとにしてSpringBootを適用したアプリ。

# SpringBootを適用するにあたり修正した箇所
### SpringBoot適用前
`**`はSpringBootを適用するにあたり削除したファイル
`*`はSpringBootを適用するにあたり修正したファイル


```
├── pom.xml *
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           ├── persistence
        │           │   ├── config **
        │           │   │   ├── DataSourceConfig.java **
        │           │   │   └── JpaConfig.java **
        │           │   ├── entity
        │           │   │   └── Employee.java
        │           │   └── repository
        │           │       └── EmployeeRepository.java *
        │           ├── service
        │           │   ├── EmployeeService.java
        │           │   ├── config **
        │           │   │   └── ServiceConfig.java **
        │           │   └── impl
        │           │       └── EmployeeServiceImpl.java
        │           └── web
        │               ├── config **
        │               │   ├── MyConfig.java **
        │               │   └── MyWebAppInitializer.java **
        │               ├── controller
        │               │   ├── EmployeeController.java
        │               │   └── IndexController.java
        │               └── form
        │                   └── EmployeeForm.java
        ├── resources
        │   ├── data.sql
        │   ├── logback.xml
        │   ├── messages.properties
        │   ├── schema.sql
        │   └── resouces *
        │       └── css
        │           └── style.css
        └── webapp **
            └── WEB-INF **
                └── templates *
                    └── employee
                        ├── index.html
                        └── insertMain.html
```


### StringBoot適用後
`**`はSpringBootを適用するにあたり新規作成したファイル
`*`はSpringBootを適用するにあたり修正したファイル


```
├── pom.xml *
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           ├── Application.java
        │           ├── persistence
        │           │   ├── entity
        │           │   │   └── Employee.java
        │           │   └── repository
        │           │       └── EmployeeRepository.java *
        │           ├── service
        │           │   ├── EmployeeService.java
        │           │   └── impl
        │           │       └── EmployeeServiceImpl.java
        │           └── web
        │               ├── controller
        │               │   ├── EmployeeController.java
        │               │   └── IndexController.java
        │               └── form
        │                   └── EmployeeForm.java
        └── resources
            ├── application.yml **
            ├── data.sql
            ├── messages.properties
            ├── schema.sql
            ├── static **
            │   └── resources *
            │       └── css
            │           └── style.css
            └── templates ** 
                └── employee
                    ├── index.html
                    └── insertMain.html
```

