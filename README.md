# Design and Implementation 
Database:   H2 Database (url jdbc:h2:mem:testdb, username:sa , paswword is empty)

Prevent multiple pods from processing the same user: Optimistic Locking can be used

Stateless Architecture: Designed for scalability in a cloud environment like Kubernetes.

Database Poller: Polls the database every 5 seconds to process users with spring  @Scheduled

There are Controller, Service and Repository packages in the application. There are related files under each package.(UserController, UserService..)

And also, Dockerfile and deployment.yml files have been added to project

### Run Locally
clone project:
```bash
https://github.com/sezayirdagtekin/task.git
```
build:
```bash
mvn clean install
```
To run:

Start the Spring Boot application using 
```bash
mvn spring-boot:run
```
Sample console output after inserting new records
```bash
2024-09-26T15:15:42.366+03:00  INFO 18588 --- [demo] [nio-8080-exec-1] com.commerzbank.service.UserSevice       : saving user:Sezayir Dagtekin
2024-09-26T15:15:42.836+03:00  INFO 18588 --- [demo] [   scheduling-1] com.commerzbank.schedule.UserProcessor   : Processing users: User(id=1, name=Sezayir, surname=Dagtekin, email=test@gmail.com, processed=false, timestamp=2024-09-26T15:15:42.357879)
2024-09-26T15:17:44.577+03:00  INFO 18588 --- [demo] [nio-8080-exec-5] com.commerzbank.service.UserSevice       : saving user:Jenifer Lopez
2024-09-26T15:17:47.843+03:00  INFO 18588 --- [demo] [   scheduling-1] com.commerzbank.schedule.UserProcessor   : Processing users: User(id=2, name=Jenifer, surname=Lopez, email=lp@gmail.com, processed=false, timestamp=2024-09-26T15:17:44.577678)

```

Access the H2 database console at
```bash
 http://localhost:8080/h2-console.
```

Test the REST endpoint using a tool like Postman or curl.

```bash
{
  "name":"Sezayir",
  "surname":"Dagtekin",
  "email":"test@gmail.com",
 "processed": false
}
```
Insert a new USER entry into the database with processed = false.
or you can use swagger
```bash
http://localhost:8080/swagger-ui/index.html
```
Application health check
```bash
  http://localhost:8080/actuator/health
```


DockerFile:
```bash

FROM openjdk:21-slim
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-app:1.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo-app:1.0.jar"]
```
Deployment.yml
```bash
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-boot-app
  labels:
    app: spring-boot-app
spec:
  replicas: 3  # work with 3 pods
  selector:
    matchLabels:
      app: spring-boot-app
  template:
    metadata:
      labels:
        app: spring-boot-app
    spec:
      containers:
      - name: spring-boot-app
        image: dockerimageUrl/demo-app:1.0 
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE 
          value: "prod"
        livenessProbe:  
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe: 
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10

---
apiVersion: v1
kind: Service
metadata:
  name: spring-boot-service
spec:
  type: LoadBalancer  # expose to external
  selector:
    app: spring-boot-app
  ports:
    - protocol: TCP
      port: 80  # external port
      targetPort: 8080  # Container port

```

Apply your yml files to Kubernetes:
```bash
kubectl apply -f deployment.yaml

```

Check if the application was deployed successfully:

```bash
kubectl get pods
kubectl get services

```

We can create an autoscaler with the following command:
```bash
kubectl autoscale deployment spring-boot-app --cpu-percent=70 --min=3 --max=8

```



