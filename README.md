# Coding assignment Backend - Engineer

============================================================================
This is my approach to create a REST API to handle charging session entities,  all records are stored in memory.

## Overview
This project was built using maven, on this repository you will find the source code. To configure and set up the application please read Configuration section.
![alt text](https://doc-0k-bs-docs.googleusercontent.com/docs/securesc/30l4mcf79an9su0mpc5u1pkf9h8lu85m/q7k7u6f02bev1uu1pim2dd3bng8477f1/1579399200000/11522828467203893858/11522828467203893858/1YwMpBHIvxiIvYJtz5YhJ59h-RKxVIgIZ?e=view&authuser=0)

### Entities
ChargingSession Entity
```java
id:            string
startedAt:  date
stationId:    string
status:     string
stoppedAt:    date
```

Summary Entity
```java
startedCount:  long
stoppedCount:  long
totalCount:       long
```
### InMemory Storage
In order to access, modify, and insert information on **log(n)** time complexity, **TreeMap** data structured was selected. Because we know that in the worst case this complexity does not change. Hashmap could be an option but in the worst case the time complexity could be **n**



## Configuration
If you need to modify this application, you can find a quick set-up below.
Move to project base path
``` shell
cd $project_path
```
If you are an **IntelliJ IDE** user 
``` shell
mvn idea:idea:
```
If you are an **Eclipse IDE** user 
``` shell
mvn eclipse:eclipse
```


## Compilation

In order to generate the JAR file run the following command.
This command is going to check the test cases also
``` shell
mvn clean package
```
It will generate a file called **charging-session-store-1.0.0-SNAPSHOT.jar** on **target** directory.

## Run jar file
After compilation we can run our application

Move to jar directory
``` shell
cd target
```

Run application command
``` shell
java -jar charging-session-store-1.0.0-SNAPSHOT.jar 
```


## Run mvn
If you do not want to create the jar file then you can use
Move project base path and run the command
``` shell
mvn spring-boot:run
```

## Usage
After running the application you can interact with the REST API. 
The application provides the following endpoints:
``` sh
GET      http://${server:port}/api/v1/chargingSessions/
POST     http://${server:port}/api/v1/chargingSessions/
GET      http://${server:port}/api/v1/chargingSessions/{id}
PUT      http://${server:port}/api/v1/chargingSessions/{id}
GET      http://${server:port}/api/v1/chargingSessions/summary
```
Default values for local running 
**server**:  localhost
**port**   :   8080

You can interact with this endpoint by using any browser or tools such as postman, swagger. 
You can see SWAGGER section to see more detail.

## SWAGGER interaction
If you are running on local, you can access to Swagger interface by your browser:
``` sh
http://localhost:8080/swagger-ui.html#/
```

Once you are on Swagger UI you will see the available endpoints

![alt text](https://doc-08-bs-docs.googleusercontent.com/docs/securesc/30l4mcf79an9su0mpc5u1pkf9h8lu85m/9285t040ngvillib0ocln8gcnonm4ue1/1579399200000/11522828467203893858/11522828467203893858/1sWMNuloWf0fqdK_KF4mYsW5iqghM9mtD?e=view&authuser=0)

You can also see the models which were created to support the functionality
![alt text](https://doc-00-bs-docs.googleusercontent.com/docs/securesc/30l4mcf79an9su0mpc5u1pkf9h8lu85m/pelv5g3lls101tmnd5o3qh704n2q5qvq/1579399200000/11522828467203893858/11522828467203893858/1kK9A5SgSyzLmaFR1AbCeUAWJthnOwBZ8?e=view&authuser=0)


## Rest API examples
### **POST** /api/v1/chargingSessions/
Entry
``` json
{
"stationId":"ABC-123451"
}
```
Response
``` json
{
    "id": "366d70a3-b056-46a9-9bd2-dd27bfb8df74",
    "stationId": "ABC-123451",
    "startedAt": "2020-01-18T20:32:24.699",
    "status": "IN_PROGRESS"
}
```
### **GET** /api/v1/chargingSessions/
Response
``` json
[
    {
        "id": "366d70a3-b056-46a9-9bd2-dd27bfb8df74",
        "stationId": "ABC-123451",
        "startedAt": "2020-01-18T20:32:24.699",
        "stoppedAt": "2020-01-18T20:32:50.89",
        "status": "FINISHED"
    }
]
```

### **PUT** /api/v1/chargingSessions/{id}
Response
``` json
{
    "id": "366d70a3-b056-46a9-9bd2-dd27bfb8df74",
    "stationId": "ABC-123451",
    "startedAt": "2020-01-18T20:32:24.699",
    "stoppedAt": "2020-01-18T20:32:50.89",
    "status": "FINISHED"
}
```

### **GET** /api/v1/chargingSessions/summary
Response
``` json
{
    "totalCount": 2,
    "startedCount": 1,
    "stoppedCount": 1
}
```

## Author

* **Luis Alberto Cortés Santiago** - ** - [Contact](https://www.linkedin.com/in/luiscs/)

