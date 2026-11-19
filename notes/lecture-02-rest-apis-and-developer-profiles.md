# Lecture 2: REST APIs and Developer Profiles

Date: 3 Nov 2026

First hands-on class: create the DevConnect Spring Boot project and add the first endpoints.

Code for this lecture: the first three commits of this repo (`First Springboot application. Health Check` and `Second Lecture - Rest API`).

## Project setup

Generated with Spring Initializr (start.spring.io):

- Build tool: Maven
- Language: Java
- Packaging: Jar
- Java: 17
- Dependency: Spring Web (`spring-boot-starter-webmvc`)
- Group `com.devconnect`, artifact `DevConnect`

Unzip, open in IntelliJ / VS Code. Folder layout:

```
src/main/java       application code
src/main/resources  config (application.properties)
src/test/java       tests
```

`pom.xml` uses `spring-boot-starter-parent` as the parent. The parent manages dependency versions so all Spring libraries stay compatible with each other.

`DevConnectApplication.main()` calls `SpringApplication.run(...)`, which starts the embedded Tomcat server. From then on Spring routes every HTTP request to the matching endpoint method.

## API and endpoint

- **API**: a contract for how two pieces of software talk. The frontend only needs the contract, not the backend internals, so both sides can be built independently.
- **Endpoint**: one specific URL + HTTP method where the API can be reached.
- Analogy: the API is a restaurant menu, each endpoint is one dish you can order.

## Endpoints built

| Method | URL | Returns |
|---|---|---|
| GET | `/health` | plain text, "service is live" message |
| GET | `/api/v1/status` | JSON: service name, status, version, timestamp |
| GET | `/api/v1/developers/search?skill=react` | developers having a skill (stubbed for now) |

`HealthController`:

```java
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "Devconnect backend is LIVE and HEALTHY!!!";
    }

    @GetMapping("/api/v1/status")
    public Map<String, Object> status() {
        Map<String, Object> map = new HashMap<>();
        map.put("Service", "DevConnect");
        map.put("Status", "Running");
        map.put("Version", "1.0.0");
        map.put("Timestamp", LocalDateTime.now().toString());
        return map;
    }
}
```

Returning a `Map` (or any Java object) from a `@RestController` method gives JSON, because Spring uses **Jackson** to convert Java objects to JSON.

## Developer model

`model/Developer.java` holds a developer profile: id, name, email, bio, city, skills, LinkedIn/GitHub URLs, years of experience, createdAt, with getters and setters.

`DeveloperController` (`/api/v1/developers`) has the first stubs: `@PathVariable`, `@RequestParam` (required / optional / default value) and `@PostMapping` with `@RequestBody`. These get real logic in Lecture 4.

## Try it

```bash
./mvnw spring-boot:run
curl localhost:8080/health
curl localhost:8080/api/v1/status
```
