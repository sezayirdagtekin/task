### Run Locally
To run:
Run mvn clean install to build the project.
```bash
mvn clean install
```
To run:

Start the Spring Boot application using 
```bash
mvn spring-boot:run
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
  "email":"test@gmail.com"
}
```
or you can use swager
```bash
http://localhost:8080/swagger-ui/index.html
```



