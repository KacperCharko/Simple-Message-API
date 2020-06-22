**This is a simple api**

Main purpose of this project was sipmle REST api building with Cassandra DB

---

## How to install 

There is docker image in my private docker hub repo. You will get access to it.

1. If you don't have cassandra already running -> ```docker run --name cassandra_db cassandra:latest -p 9042:9042```.
2. Run project

## **ERROR##**
Application work as expected when I run it using InteliJ or by `java -jar` in command lane. Problem occurs when app container
is trying to reach cassandra container. App is unable to reach database
You will probably get exception `AllNodesFailedException: Could not reach any contact point, make sure you've provided valid addresses`
I was not able to solve this problem. I have made huge reasearch on how to deal with that but no ideas I found or came up worked

what I have tried so far:

1. Link both containers
2. pair both containers in same network
3. seraching in documentation
4. I was looking for help in stackoverflow : https://stackoverflow.com/questions/62357717/
5. use docker-compose to automatize app and db creation


```
version: "3.8"
services:

  app:
    build: ./
    ports:
      - "8080:8080"
    networks:
      - mynet
    links:
      - "db:db"
    restart: always        
  
  db:
    image: cassandra
    networks:
      - mynet
    ports:
        - "9042:9042"


networks:
  mynet:
    driver: bridge
```


---

## How to consume API

some queries below.

**to create message** 

```curl -X POST localhost:8080/api/message -d '{"email":"jan.kowalski@example.com","title":"Interview","content":"simple text","magic_number":101}' -H "Content-Type: application/json"```

```curl -X POST localhost:8080/api/message -d '{"email":"jan.kowalski@example.com","title":"Interview2","content":"simple text2","magic_number":22}' -H "Content-Type: application/json"```

```curl -X POST localhost:8080/api/message -d '{"email":"anna.zajkowska@example.com","title":"Interview3","content":"simple text3","magic_number":101}' -H "Content-Type: application/json"```

**to get messages by email**

```curl -X GET 'http://localhost:8080/api/messages/anna.zajkowska@example.com'```

**to send all messages with given number**

```curl -X POST localhost:8080/api/send -d '{"magic_number":101}' -H "Content-Type: application/json"```

---
