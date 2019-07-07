# Kalah Game


## Tech Stack

  * Java 8
  * Spring Boot 2.1.6
  * H2 Database
  * Lombok
  * JUnit 5
  * Swagger

## Running 

### Run with Gradle

Build:

```bash
./gradlew clean build
```

Run tests only:

```bash
./gradlew clean test
```

Run the application:

```bash
./gradlew bootRun
```

### Run in Docker container

Build:
```bash
./gradlew clean jibDockerBuild
```

Run application in Docker container:
```bash
docker run -p 8080:8080 kalah-game:1.0.0
```

### Run as JAR file

Build:
```bash
./gradlew clean build
```

Run the application:

```bash
java -jar build/libs/kalah-game-1.0.0.jar
```

## Swagger API Documentation


```bash
http://localhost:8080/swagger-ui.html
```

### Test Reports

HTML test reports are generated under `build/reports/tests/test`

## Improvements
* Add optimistic locking mechanism with `@Version`, `ETag` and `If-Match`
