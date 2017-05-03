# OAuth2を使った簡単なサンプルアプリ
Pro Spring Boot 第９章を参考に作った。

## OAuth2.0の仕様
### 概要図

```
+---------+                               +---------------+
|         |------ Authorization Request ->|   Resource    |
|         |                               |     Owner     |
|         |<------ Authorization Grant ---|               |
|         |                               +---------------+
|         |
|         |                               +---------------+
| Client  |------- Authorization Grant -->| Authorization |
|         |                               |     Server    |
|         |<--------- Access Token -------|               |
|         |                               +---------------+
|         |
|         |                               +---------------+
|         |---------- Access Token ------>|    Resource   |
|         |                               |     Server    |
|         |<------- Protected Resource ---|               |
+---------+                               +---------------+

```


### 登場人物
* Client
  * 権限に基づき、Rsource Ownerの代理としてリソースを要求するアプリケーション。
* Resource Owner
  * リソースへのアクセス権を与える人。
* Autorization Server
  * Resourc Ownerがアクセス権を与えたあと、クライアントに対しアクセストークンを発行する。
* ResourceServer
  * 送られてきたアクセストークンを検証し、正しい場合は、リクエストに対するレスポンスを返す。
  
## サンプルアプリの場合
サンプルアプリは`http://localhost:8080/api/**`に対してはOAuth2による認可が必要になっている。
アプリ起動直後はユーザーが`http://localhost:8080/api`にアクセスしても、
`Full authentiatoin is required to access this resource.`
のようなメッセージを含むXMLがレスポンスで返ってきてリソースにアクセスできないようになっている。

### 概要
```
+------------------------+                                 +-------------------+
| Sample App             |                                 |  User             |
|                        |<-(1)-----------------------(2)->|                   |
| + clientId             |                                 |                   |
| + secretKey            |                                 |                   |
|                        |                                 |                   |
| (Authorization Server) |<-(4)- Authorization Grant -(3)--|  (Client)         |
|                        |                                 |  (Resource Owner) |
| + access_token         |--(4)--- Access Token ------(5)->|                   |
|                        |                                 |                   |
|                        |                                 |                   |
|                        |                                 |                   |
| (Resource Server)      |<-(7)----- Access Token ----(6)--|                   |
|                        |                                 |                   |
|                        |--(7)- Protected Resource --(8)->|                   |
+------------------------+                                 +-------------------+

```

## 登場人物と役割
* Client
  * 今回は簡単のためサンプルアプリを使用する人がこの役を担う。
* Resource Owner
  * 今回はサンプルアプリを使用する人がこの役を担う。
* Autorization Server
  * 今回は簡単のためサンプルアプリがこの役を兼任する。
* ResourceServer
  * 今回は簡単のためサンプルアプリがこの役を兼任する。


## サンプルアプリの認証手順
1. サンプルアプリを起動するとログにクライアントID（clientId）と秘密鍵（secretKey）が出力される。
2. ユーザはログを確認してクライアントIDと秘密鍵をメモする。
3. ユーザはターミナルにて認証サーバ（サンプルアプリ）に下記リクエストを送信する。
```
$ curl -i localhost:8080/oauth/token  \
　　　　-d "grant_type=password&scope=read&username=[サンプルアプリのユーザーID]&password=[サンプルアプリのパスワード]"  \
　　　　-u [クライアントID]:[秘密鍵]
```
4. サンプルアプリはリクエストで送信されたユーザID、パスワード、クライアントID、秘密鍵を確認し、正常な場合はアクセストークン（access_token）をレスポンスで返す。


5. 成功すると下記のようにレスポンスが返ってくるので、アクセストークン(access_token)とアクセストークンの型（token_type）をメモする。
```
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 03 May 2017 09:32:55 GMT

{
  "access_token":"70891d6e-a99f-49e2-a721-76b6d2004516",
  "token_type":"bearer",
  "refresh_token":"2ad8dff3-c002-48d9-9853-9bd34e4997c0",
  "expires_in":43199,"scope":"read"
}
```

6. ユーザはターミナルにてリソースサーバ（サンプルアプリ）に下記リクエストを送信する。
```
$ curl -i -H "Authorization:[アクセストークンの型] [アクセストークン]" localhost:8080/api
```

7. サンプルアプリはリクエストで送信されたアクセストークンの情報を検証し、正常な場合、リソースをレスポンスで返す。
8. 下記のようにレスポンスが返ってくれば成功。
```
HTTP/1.1 200 
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 03 May 2017 09:39:12 GMT

{
  "_links" : {
    "entry" : {
      "href" : "http://localhost:8080/api/journal"
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile"
    }
  }
}
```
