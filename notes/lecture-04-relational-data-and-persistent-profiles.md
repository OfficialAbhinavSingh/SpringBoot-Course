# Lecture 4: Relational Data and Persistent Profiles

Date: 17 Nov 2026

This class built the full REST API for developer profiles: REST constraints, URL design, CRUD with HTTP methods, path variables, request params, request bodies. Data is stored in memory for now (a `ConcurrentHashMap`); a real database comes later.

## REST constraints

- Client-server: frontend and backend are separate
- Stateless: every request carries everything needed; the server keeps no session between requests
- Cacheable: responses can say whether they may be cached
- Uniform interface: resources are identified by URLs and handled with standard HTTP methods

An endpoint is **HTTP method + URL**, not just the URL. `GET /api/v1/developers` and `POST /api/v1/developers` are two different endpoints.

## HTTP methods (CRUD)

| Method | Meaning | DevConnect endpoint |
|---|---|---|
| GET | read | `GET /api/v1/developers`, `GET /api/v1/developers/{id}` |
| POST | create | `POST /api/v1/developers` |
| PUT | replace the whole resource | `PUT /api/v1/developers/{id}` |
| PATCH | update part of a resource | `PATCH /api/v1/developers/{id}` |
| DELETE | remove | `DELETE /api/v1/developers/{id}` |

## URL design rules

- Nouns, never verbs: `/developers`, not `/getDevelopers`
- Plurals for collections: `/developers`, not `/developer`
- Lowercase, hyphens for readability
- Hierarchy for nested resources: `/projects/{projectId}/members`
- Version the API in the path: `/api/v1`, `/api/v2`, so old clients keep working when the API changes

## In-memory store

```java
private final Map<Long, Developer> developerStore = new ConcurrentHashMap<>();
private final AtomicLong idCounter = new AtomicLong(1);
```

- `ConcurrentHashMap`: thread-safe, many requests can hit it at the same time
- `AtomicLong`: thread-safe id generator
- Everything is lost on restart (persistence comes with the database lectures)

## Path variables, request params, request body

```java
// GET /api/v1/developers/5
@GetMapping("/{id}")
public Developer getDeveloperById(@PathVariable Long id) {
    return developerStore.getOrDefault(id, new Developer());
}

// GET /api/v1/developers/search?skill=React
@GetMapping("/search")
public List<Developer> getDevelopersBySkill(@RequestParam(required = false) String skill) {
    ...
    return developerStore.values().stream()
            .filter(developer -> developer.getSkills().stream()
                    .anyMatch(s -> s.equalsIgnoreCase(skill)))
            .collect(Collectors.toList());
}

// POST /api/v1/developers  with a JSON body
@PostMapping
public Developer createDeveloper(@RequestBody Developer developer) {
    developer.setId(idCounter.getAndIncrement());
    developerStore.put(developer.getId(), developer);
    return developer;
}
```

- `@PathVariable`: a dynamic part of the path becomes a method argument
- `@RequestParam`: query parameter (`?skill=React`); `required = false` makes it optional, `defaultValue` gives a fallback
- `@RequestBody`: Jackson converts the JSON body into a `Developer`, matching JSON keys to field names

Missing ids still return an empty `Developer`. Proper status codes and error responses are the topic of the "Validation and Predictable API Errors" lecture.

## Model changes

- Added `phoneNumber` to `Developer`
- Fixed `setSkills` (the "Is this method safe?" question from Lecture 2). The old version only assigned when the incoming list was `null`, so skills sent in a JSON body were dropped and search by skill could never match. It now stores a copy of the list and never stores `null`. `addSkills` no longer sets the list to `null` either.

## Try it

```bash
B=localhost:8080/api/v1/developers

curl -X POST $B -H 'Content-Type: application/json' \
  -d '{"name":"Riya","email":"riya@test.com","phoneNumber":"9999999999","skills":["Java","React"]}'

curl $B
curl $B/1
curl "$B/search?skill=react"

curl -X PUT   $B/1 -H 'Content-Type: application/json' -d '{"name":"Riya S","email":"riya@test.com","skills":["Go"]}'
curl -X PATCH $B/1 -H 'Content-Type: application/json' -d '{"city":"Bangalore"}'
curl -X DELETE $B/1
```
