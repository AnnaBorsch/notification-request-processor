# Request Processor

**Описание:** Сервис обработки уведомлений с гарантированной доставкой.

---

## Описание сервиса

Request Processor принимает уведомления через API, обрабатывает их и отправляет в соответствующие Kafka-топики.  
Сервис реализует:

- API для приёма уведомлений (SMS, EMAIL, PUSH, TG_MESSAGE)
- Выбор обработчика уведомления с помощью паттерна **Strategy**
- Гарантированную доставку сообщений через **Transactional Outbox**
- Логирование подготовленных сообщений
- Шедулер для периодической отправки сообщений в Kafka

---

## Технологический стек

- Java 21
- Spring Boot 3.5.x
- Apache Kafka
- PostgreSQL

---

## API

### POST `/api/v1/notifications`

**Request Body:**

```json
{
  "type": "SMS|EMAIL|PUSH|TG_MESSAGE",
  "message": "string"
}