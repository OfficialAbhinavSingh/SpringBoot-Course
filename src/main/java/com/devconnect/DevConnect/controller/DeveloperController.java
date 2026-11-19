package com.devconnect.DevConnect.controller;

import com.devconnect.DevConnect.model.Developer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

// REST URL rules followed here:
//  - nouns, never verbs           -> /developers, not /getDevelopers
//  - plural for collections       -> /developers, not /developer
//  - lowercase, hyphens if needed
//  - nested resources as a path   -> /projects/{projectId}/members
//  - version in the path          -> /api/v1/...
// An endpoint = HTTP method + URL. GET /developers and POST /developers are different endpoints.
@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperController {

    // ─── In-Memory Storage ────────────────────────────────────────
    // ConcurrentHashMap is thread-safe — safe for concurrent requests
    // AtomicLong is thread-safe for ID generation
    // WARNING: All data is lost when the server restarts!
    private final Map<Long, Developer> developerStore = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // GET /api/v1/developers  -> all developers
    @GetMapping
    public List<Developer> getAllDevelopers() {
        return new ArrayList<>(developerStore.values());
    }

    // GET /api/v1/developers/search?skill=React
    // Declared before "/{id}" for readability; Spring picks the literal path "/search" over the variable one anyway.
    @GetMapping("/search")
    public List<Developer> getDevelopersBySkill(@RequestParam(required = false) String skill) {
        if (skill == null || skill.isBlank()) {
            return getAllDevelopers();
        }
        return developerStore.values().stream()
                .filter(developer -> developer.getSkills().stream()
                        .anyMatch(s -> s.equalsIgnoreCase(skill)))
                .collect(Collectors.toList());
    }

    // GET /api/v1/developers/{id}  -> path variable
    @GetMapping("/{id}")
    public Developer getDeveloperById(@PathVariable Long id) {
        return developerStore.getOrDefault(id, new Developer());
    }

    // POST /api/v1/developers  -> create
    // Body: {"name":"Riya","email":"riya@test.com","phoneNumber":"9999999999","skills":["Java","React"]}
    // @RequestBody: Jackson maps the JSON fields to the Developer fields by name
    @PostMapping
    public Developer createDeveloper(@RequestBody Developer developer) {
        developer.setId(idCounter.getAndIncrement());
        developerStore.put(developer.getId(), developer);
        return developer;
    }

    // PUT /api/v1/developers/{id}  -> replace the whole resource
    @PutMapping("/{id}")
    public Developer replaceDeveloper(@PathVariable Long id, @RequestBody Developer developer) {
        if (!developerStore.containsKey(id)) {
            return new Developer();
        }
        developer.setId(id);
        developerStore.put(id, developer);
        return developer;
    }

    // PATCH /api/v1/developers/{id}  -> update only the fields that were sent
    @PatchMapping("/{id}")
    public Developer updateDeveloper(@PathVariable Long id, @RequestBody Developer updates) {
        Developer existing = developerStore.get(id);
        if (existing == null) {
            return new Developer();
        }
        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getEmail() != null) existing.setEmail(updates.getEmail());
        if (updates.getPhoneNumber() != null) existing.setPhoneNumber(updates.getPhoneNumber());
        if (updates.getBio() != null) existing.setBio(updates.getBio());
        if (updates.getCity() != null) existing.setCity(updates.getCity());
        if (!updates.getSkills().isEmpty()) existing.setSkills(updates.getSkills());
        if (updates.getLinkedInURL() != null) existing.setLinkedInURL(updates.getLinkedInURL());
        if (updates.getGithubInURL() != null) existing.setGithubInURL(updates.getGithubInURL());
        if (updates.getYearsOfExperience() != null) existing.setYearsOfExperience(updates.getYearsOfExperience());
        return existing;
    }

    // DELETE /api/v1/developers/{id}
    @DeleteMapping("/{id}")
    public String deleteDeveloper(@PathVariable Long id) {
        Developer removed = developerStore.remove(id);
        return removed == null ? "Developer " + id + " not found" : "Developer " + id + " deleted";
    }
}
