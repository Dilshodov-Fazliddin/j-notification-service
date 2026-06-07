# j-notification-service

A Spring Boot microservice for sending SMS and email notifications via Apache Kafka, with per-merchant webhook callbacks.

## Tech Stack

- **Java 21** / Spring Boot 3.5
- **Apache Kafka** — async notification delivery
- **PostgreSQL** — persistent storage
- **Flyway** — database migrations
- **Spring Security** — HTTP Basic auth per merchant
- **Spring Mail** — SMTP email delivery
- **MapStruct** — DTO/entity mapping

## Architecture

```
Client → REST API → NotificationService → Kafka Producer
                                                ↓
                                        Kafka Consumer
                                                ↓
                                    EmailService / SmsService
                                                ↓
                                    WebhookService → Merchant webhook URL
```

Each merchant registers with a webhook URL. When a notification is delivered (or fails), the service POSTs a status update to that URL.

## Prerequisites

- Docker & Docker Compose
- Java 21+

## Getting Started

**1. Start infrastructure**

```bash
docker-compose up -d
```

This starts PostgreSQL (port 5432), Kafka (port 9092), and Kafka UI (port 8080).

**2. Configure environment**

Copy the application config and fill in credentials:

```yaml
# src/main/resources/application.yml
spring:
  mail:
    username: your-email@gmail.com
    password: your-app-password
  datasource:
    url: jdbc:postgresql://localhost:5432/notification
    password: postgres
```

**3. Run the service**

```bash
./gradlew bootRun
```

The service starts on port **8085**.

## API Endpoints

All endpoints except registration require HTTP Basic authentication (merchant `login:password`).

### Register a merchant

```
POST /api/j-notification/registration
Content-Type: application/json

{
  "companyName": "Acme Corp",
  "taxNumber": "123456789",
  "login": "acme",
  "password": "secret",
  "webhook": "https://acme.com/notifications/webhook"
}
```

### Send SMS notification

```
POST /api/j-notification/sms/{merchantId}
Authorization: Basic <base64(login:password)>
Content-Type: application/json

{
  "receiver": "998901234567",
  "content": "Your order is confirmed."
}
```

### Send email notification

```
POST /api/j-notification/email/{merchantId}
Authorization: Basic <base64(login:password)>
Content-Type: application/json

{
  "email": "user@example.com",
  "content": "Your order has been shipped."
}
```

## Webhook Payload

After each notification attempt the service POSTs to the merchant's webhook URL:

```json
{
  "notificationId": 42,
  "type": "SMS",
  "status": "SENT",
  "receiver": "998901234567"
}
```

`status` is one of: `CREATED`, `SENT`, `FAILED`.

## Kafka Topics

| Topic               | Producer              | Consumer        |
|---------------------|-----------------------|-----------------|
| `email-notifications` | `NotificationService` | `ConsumerEmail` |
| `sms-notifications`   | `NotificationService` | `ConsumerSms`   |

## Database

Migrations are managed by Flyway and run automatically on startup. Schema lives in `src/main/resources/db/migration/`.

## Running Tests

```bash
./gradlew test
```

## Monitoring

Kafka UI is available at [http://localhost:8080](http://localhost:8080) after running `docker-compose up`.
