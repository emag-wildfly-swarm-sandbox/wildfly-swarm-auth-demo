# wildfly-swarm-auth-demo

WildFly Swarm auth(Database Realm) Demo

## Usage

###  Run the app

``` sh
$ ./mvnw clean wildfly-swarm:run
```

### Access protected API

``` sh
$ curl localhost:8080 -v
[...]
< HTTP/1.1 401 Unauthorized
[...]
```

``` sh
$ curl --user Penny:password1 localhost:8080 | jq .
[
  {
    "id": 0,
    "name": "Penny",
    "password": "password1",
    "role": "admin"
  }
]
```