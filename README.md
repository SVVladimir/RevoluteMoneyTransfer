# Revolute Money Transfer

## Build
Minimum requirements:
* Apache Maven 3.3.3
* JDK 1.8

Run from project root:

```
mvn clean install
```

Output directory *./target

## Run

Run from project root:

```
java -jar target/money-transfer-1.0-SNAPSHOT.jar
```

## Usage

Root - http://localhost:8080/

### Endpoints

| HTTP method | Path | Description |
| -----------| ------ | ------ |
| POST | /account | create account |
| GET | /account/{id} | get account |
| GET | /account/all | get all accounts |
| PUT | /account/{id} | update account |
| DELETE | /account/{id} | delete account |
| POST | /transfer | make transfer |
| DELETE | /transfer/{id}" | rollback transfer |

### Input JSON example

##### Account: : 

```
{
    "name":"Test account",
    "balance":10.0,
}
```

#### Transfer:
```
{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 10.0
}
```

