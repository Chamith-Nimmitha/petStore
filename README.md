### Chamith Nimmitha
#### 18000827
#### SCS3203 / IS3108 Middleware Architecture
# Pet Store

## To build and deploy the API

Run the following commands and up the swagger and refer the End points

##### step 1:
run the command on terminal

    ./gradlew build -Dquarkus.package.type=uber-jar

##### step 2
run the command on terminal

    java -jar build/petStore-runner.jar


## Run Test Suite
run the command on terminal

    ./gradlew test

## CURL/WGET commands

### Pets
Get the all the pets

    curl -X GET "http://localhost:8080/pets" -H  "accept: application/json"

Create a new pet

    curl -X POST "http://localhost:8080/pets" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petAge\":3,\"petName\":\"doggy\",\"petType\":\"dog\"}"

Update an existing pet

    curl -X PATCH "http://localhost:8080/pets/1" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petAge\":3,\"petName\":\"dikky\",\"petType\":\"dog\"}"

Delete an existing pet

    curl -X DELETE "http://localhost:8080/pets/3" -H  "accept: */*"

Search pets with name/type/age

    curl -X POST "http://localhost:8080/pets/search" -H  "accept: application/json" -d "{\"age\":3,\"name\":\"doggy\",\"type\":\"dog\"}"

Get pet by ID

    curl -X GET "http://localhost:8080/pets/3" -H  "accept: application/json"

### Pet Types

Get all pet types

    curl -X GET "http://localhost:8080/pets/types" -H  "accept: application/json"

Create a new pet type

    curl -X POST "http://localhost:8080/petd/types" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"petId\":4,\"petType\":\"lion\"}"

Update an existing pet type

    curl -X PATCH "http://localhost:8080/pets/types/1" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"petId\":1,\"petType\":\"dogs\"}"

Delete an existing  pet type

    curl -X DELETE "http://localhost:8080/pets/types/1" -H  "accept: application/json"


#### Schemas
Pet Schema

    {
    "petAge": int,
    "petId": int,
    "petName": "string",
    "petType": "string" 
    }

PetType Schema

    {
    "petId": int,
    "petType": "string"
    }

SearchPet Schema

    {
    "age": int,
    "name": "string",
    "type": "string"
    }
    