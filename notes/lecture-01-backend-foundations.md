# Lecture 1: Backend Foundations, Why Frameworks, Why Spring Boot?

Date: 30 Oct 2026

Theory class. No code was written in this lecture; the project setup starts in Lecture 2.

## What a backend does

The backend is the server side of an application. It sits between the client (web/mobile frontend) and the database.

Main responsibilities:

- Routing: send each incoming request to the right piece of logic
- Data persistence: read from and write to the database
- Security: authentication and authorization
- Business logic: the actual rules of the application

## Framework vs library

| | Library | Framework |
|---|---|---|
| Who is in control | Your code calls the library when it needs it | The framework calls your code |
| Idea | You own the flow | Inversion of Control (IoC) |

A framework gives a ready structure and handles infrastructure work (server, request handling, wiring objects together) so we only write business logic.

## Why Spring Boot

- Java is still one of the main languages for large backend systems (Netflix, Amazon etc. run Spring Boot services)
- Built on the mature Spring Framework, integrates easily with databases, cloud providers, message brokers

Key features:

- **Auto-configuration**: configures the app based on the dependencies present
- **Embedded server**: ships with Tomcat, so the app runs as a plain `java -jar`
- **Convention over configuration**: sensible defaults, less setup
- **Spring Initializr** (start.spring.io): generates the standard project structure

### Spring Framework vs Spring Boot

- **Spring Framework**: the core framework (dependency injection, transactions, MVC)
- **Spring Boot**: Spring + auto-configuration + embedded server + starter dependencies, so far less setup

## Course structure

- Lectures for concepts, weekly labs for implementation
- Assignment types: build (implement a feature), refactor (improve existing code), debug (find and fix bugs in a repo)
- GitHub workflow: clone, change, open a pull request for evaluation
- One running project (**DevConnect**) is built across the whole course, from basic APIs to a complete backend
