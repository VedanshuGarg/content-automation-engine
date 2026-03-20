# 🚀 The 1% Club: Content Automation Engine

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.x-brightgreen.svg)
![Spring Security OAuth2](https://img.shields.io/badge/Security-OAuth2-blue.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)

An enterprise-grade social media scheduling and automation backend designed to manage content distribution across YouTube and Instagram. 

This service acts as a centralized brain for content publishing, utilizing asynchronous processing, automated cron jobs, and external API integrations with OAuth2 authorization flows.



## 🏗️ Architecture & Core Features

* **Cron-Driven Scheduling:** Utilizes Spring's `@Scheduled` tasks to poll the database for pending content and trigger publishing workflows at exact timestamps.
* **OAuth2 Integration:** Manages secure, token-based authentication with the YouTube Data API v3 and Instagram Graph API.
* **Resilient API Clients:** Implements Spring WebFlux (`WebClient`) for non-blocking external API calls, complete with exponential backoff for rate limiting.
* **State Machine Logic:** Tracks the lifecycle of a post (`DRAFT`, `SCHEDULED`, `PUBLISHED`, `FAILED`) to ensure idempotency and prevent duplicate uploads.

## 🛠️ Tech Stack
* **Core:** Java 17, Spring Boot 3.x
* **Persistence:** PostgreSQL via Spring Data JPA
* **Web Client:** Spring WebFlux
* **Security:** Spring Boot OAuth2 Client

## 📡 Core Domain Model

The primary entity is the `ScheduledPost`, which stores the asset metadata, target platform (Shorts/Reels), and execution timestamp.

```json
{
  "id": "uuid",
  "platform": "YOUTUBE_SHORTS",
  "title": "The Psychology of Success",
  "description": "Join the 1% Club...",
  "mediaUrl": "[https://s3.amazonaws.com/](https://s3.amazonaws.com/)...",
  "scheduledTime": "2026-03-25T14:00:00Z",
  "status": "SCHEDULED"
}
