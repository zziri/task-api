# task api

Task List 를 관리할 수 있는 App 의 API 서버

# dev environment

- Java 8
- Spring boot 2.4
- Gradle
- JPA
- AWS (EC2, RDS MariaDB)
- OAuth 2.0

# API Docs

API 문서입니다

## Sign up + Sign in

소셜 계정으로 회원가입과 동시에 사용자 인증을 합니다

이미 가입된 회원이라면 회원가입은 건너뜁니다

Google, Kakao 로그인을 지원합니다

이 요청으로 얻은 인증 토큰은 이후 `Task-Authentication` header 로 다른 요청에 사용합니다



<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

소셜 서비스의 OAuth access token 을 query string에 담아서 요청합니다

```HTTP
POST /v2/auth/{social}?accessToken={access token}
```

### Response

성공하면 `200 OK`를 반환하고 body 에 JSON 형태로 errer, data 를 담아주며 data 에는 이후 API를 사용하기 위한 인증 토큰이 있습니다

### Example

Sign up + Sign in 의 예제입니다

#### Request

```HTTP
POST https://task.zziri.me/v2/auth/google?accessToken=123456789123456789123456789
```

#### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json

{
    "error": false,
    "data": "987654321987654321iI0NiIsInJvbGVzIjp"
}
```

</details>



## Get UserInfo

User 정보를 읽어옵니다

account, name, provider 속성을 User 정보에 담아서 Response 합니다

account는 unique한 계정정보, name은 사용자의 이름, provider는 회원가입할 때에 이용한 소셜서비스(Google or Kakao)입니다

<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

```HTTP
GET /v2/user
```

### Request Headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Response

성공하면 `200 OK`를 반환하고 body 에 JSON 형태로 errer, data 를 담아주며 data 에는 user info 의 속성들이 있습니다

### Example

Get UserInfo 의 예제입니다

#### Request

```HTTP
GET https://task.zziri.me/v2/user
```

#### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json

{
    "error": false,
    "data": {
        "account": "1234567890151358",
        "name": "zziri",
        "provider": "google"
    }
}
```

</details>

## Update UserInfo

User 정보를 수정합니다

account, provider 정보는 수정할 수 없습니다

<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

```HTTP
PATCH /v2/user
```

### Request Headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Request Body

UserInfo 의 속성을 Body에 담아 요청할 수 있습니다. 아래 내용 이외에 다른 속성을 포함하면 무시됩니다.

|Property|Type|Description|
|---|:---:|:---:|
|name|String|사용자의 이름 필드|

### Response

성공하면 `200 OK`를 반환하고 body 에 JSON 형태로 errer, data 를 담아주며 data 에는 수정 후의 user info 의 속성들이 있습니다

### Example

Update UserInfo 의 예제입니다

#### Request

```HTTP
PATCH https://task.zziri.me/v2/user
Content-Type: application/json

{
    "name": "jihoon"
}
```

#### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json

{
    "error": false,
    "data": {
        "account": "1234567890151358",
        "name": "jihoon",
        "provider": "google"
    }
}
```

</details>

## Get Tasks

사용자의 모든 task를 읽어옵니다

사용자가 등록한 모든 task의 id, title, memo, completed, createdAt, modifiedAt 속성을 담아서 리스트로 반환합니다

id는 task의 unique한 key입니다

<details markdown="1">
<summary>Click for details</summary>


### HTTP Request

```HTTP
GET /v2/tasks
```

### Request headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Response

성공하면 `200 OK` 를 반환하고 body에 JSON 형태로 error, data 를 담아주며 data 에는 list 형태로 task 들의 속성들이 있습니다

### Examples

Get Tasks 의 예제입니다

#### Request

```HTTP
GET https://task.zziri.me/v2/tasks
```
#### Response

```HTTP
HTTP/1.1 200 OK
Content-Type: application/json

{
    "error": false,
    "data": [
        {
            "id": 65,
            "title": "this is task's title",
            "memo": "this is task's memo",
            "completed": false,
            "createdAt": "2021-06-02T15:48:36",
            "modifiedAt": "2021-06-02T15:48:36"
        }
    ]
}
```

</details>

## Create Task

task 를 추가합니다

completed, title, memo 속성을 포함하여 요청합니다

만약 무시할 경우 completed는 false, title 과 memo 는 ""(빈 문자열)로 초기화됩니다

내용이 같은 task 를 중복으로 추가하는 것을 허용합니다

<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

```HTTP
POST /v2/tasks
```

### Request headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Request body

task 의 속성들을 JSON 으로 표현해서 body 에 담아 요청합니다

|Property|Type|Description|
|---|:---:|:---:|
|completed|boolean|task가 완료되었는지 나타내는 속성|
|title|String|task의 제목 필드|
|memo|String|task의 메모 필드|

### Response

성공하면 `201 Created` 상태 코드와 함께 새로 생성된 task 를 response body 에 반환합니다

### Examples

Create Task 의 예제입니다

#### Request

```HTTP
POST https://task.zziri.me/v2/tasks
Content-Type: application/json

{
    "title": "test title"
}
```
#### Response

```HTTP
HTTP/1.1 201 Created
Content-Type: application/json

{
    "error": false,
    "data": {
        "id": 144,
        "title": "test title",
        "memo": "",
        "completed": false,
        "createdAt": "2021-06-17T14:26:43.954",
        "modifiedAt": "2021-06-17T14:26:43.954"
    }
}
```

</details>

## Update Task

task 내용을 수정합니다

completed, title, memo 속성을 수정할 수 있으며 다른 속성을 포함하면 무시됩니다

위 속성들 중 request body에 포함되지 않은 속성은 기존의 값을 유지합니다

<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

```HTTP
PATCH /v2/tasks/{taskId}
```

### Request headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Request body

task 의 속성들을 JSON 으로 표현해서 body 에 담아 요청합니다

|Property|Type|Description|
|---|:---:|:---:|
|completed|boolean|task가 완료되었는지 나타내는 속성|
|title|String|task의 제목 필드|
|memo|String|task의 메모 필드|

### Response

성공하면 `200 OK` 상태 코드와 함께 수정 후 task 정보를 response body 에 반환합니다

### Examples

Update Task 의 예제입니다

#### Request

```HTTP
PATCH https://task.zziri.me/v2/tasks/144
Content-Type: application/json

{
    "title": "update"
}
```
#### Response

```HTTP
HTTP/1.1 200 Created
Content-Type: application/json

{
    "error": false,
    "data": {
        "id": 144,
        "title": "update",
        "memo": "",
        "completed": false,
        "createdAt": "2021-06-17T14:26:43.954",
        "modifiedAt": "2021-06-20T16:08:11.233"
    }
}
```

</details>

## Delete Task

task 정보를 삭제합니다

삭제한 task 정보는 따로 백업하지 않아 되돌릴 수 없습니다

<details markdown="1">
<summary>Click for details</summary>

### HTTP Request

```HTTP
DELETE /v2/tasks/{taskId}
```

### Request headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Response

성공하면 `200 OK` 상태 코드와 함께 삭제 성공 여부를 response body 에 반환합니다

### Examples

Delete Task 의 예제입니다

#### Request

```HTTP
DELETE https://task.zziri.me/v2/tasks/144
```
#### Response

```HTTP
HTTP/1.1 200 Created
Content-Type: application/json

{
    "error": false,
    "data": null
}
```

</details>