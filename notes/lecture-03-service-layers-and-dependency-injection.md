# Lecture 3: Service Layers and Dependency Injection

Date: 16 Nov 2026

This class went through how a Spring Boot app is put together: Maven, configuration, annotations, beans and Inversion of Control, then REST controller basics and returning a list of developers.

## Maven

- Build tool: downloads dependencies, compiles, packages the app into a runnable jar
- `spring-boot-starter-parent` in `pom.xml` pins versions of all Spring dependencies so they work together with our Java version

## application.properties

Spring Boot config lives in `src/main/resources/application.properties`:

```properties
spring.application.name=DevConnect
server.port=8080
```

`server.port` decides which port the embedded server listens on.

## Package structure

Split code into packages so it stays readable and easy to extend:

```
com.devconnect.DevConnect
├── controller   HTTP layer (HealthController, DeveloperController)
└── model        data classes (Developer)
```

Keeping responsibilities in separate packages follows the SOLID idea of decoupling (SOLID itself is covered properly in the LLD course).

## Core annotations

`@SpringBootApplication` is three annotations combined:

| Annotation | What it does |
|---|---|
| `@SpringBootConfiguration` | marks the class as a source of Spring beans |
| `@EnableAutoConfiguration` | configures the app based on the dependencies on the classpath |
| `@ComponentScan` | scans the package and sub-packages for components (`@RestController`, `@Service`, `@Component` ...) |

## Beans and Inversion of Control

- A **bean** is an object that Spring creates and manages (its lifecycle and its dependencies)
- **Inversion of Control**: we don't `new` the controllers ourselves, the framework creates them and calls our methods when a request comes in
- Library vs framework again: with a library we keep control, with a framework the framework is in control

## REST controller basics

- `@RestController`: class that handles HTTP requests, return values are written as the response body (JSON)
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`: map a method to an HTTP method + path
- `@RequestMapping("/api/v1/developers")` on the class: common prefix for every method in it

## Code added in this lecture

`GET /api/v1/developers` returns a list of developers:

```java
@GetMapping
public List<Developer> getDevelopers() {
    List<Developer> developers = new ArrayList<>();
    developers.add(new Developer(1L, "Riya", "riya@test.com"));
    developers.add(new Developer(2L, "Aman", "aman@test.com"));
    developers.add(new Developer(3L, "Sara", "sara@test.com"));
    return developers;
}
```

Jackson converts the `List<Developer>` to a JSON array using the getters of `Developer`.

Also added comments in `DevConnectApplication` explaining `@SpringBootApplication` and IoC.

## Try it

```bash
curl localhost:8080/api/v1/developers
```
