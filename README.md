# SpringBoot-Course: DevConnect

Code and notes from the SST "Intro to Spring Boot" course. The course builds one project, **DevConnect**, a backend where students find teammates for hackathons based on their skills. The code grows lecture by lecture; each lecture is one commit and has a git tag (`lecture-01`, `lecture-02`, ...).

## Lectures

| # | Date | Topic | Code | Notes |
|---|------|-------|------|-------|
| 1 | 30 Oct | Backend Foundations: Why Frameworks, Why Spring Boot? | theory only | [notes](notes/lecture-01-backend-foundations.md) |
| 2 | 3 Nov | REST APIs and Developer Profiles | project setup, `/health`, `/api/v1/status`, `Developer` model | [notes](notes/lecture-02-rest-apis-and-developer-profiles.md) |

## Run

Needs Java 17+.

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`.
