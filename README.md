# task api

Task List 를 관리할 수 있는 App 의 API 서버

# dev environment

- Java 8
- Spring boot 2.4
- Gradle
- JPA
- AWS (EC2, RDS MariaDB)
- OAuth 2.0

# api docs

API 문서입니다

## Get UserInfo

User 정보를 읽어옵니다

### HTTP Request

```HTTP
GET /v2/user
```

### query parameters

query param은 필요하지 않습니다

### Request Headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Request body

body는 필요하지 않습니다

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
        "provider": ""
    }
}
```

## Get Tasks

User 의 모든 task를 읽어옵니다

### HTTP Request

```HTTP
GET /v2/tasks
```

### query parameters

query param은 필요하지 않습니다

### Request headers

|Name|Description|
|---|:---:|
|Task-Authentication|{token}|

### Request body

body 는 필요하지 않습니다

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
