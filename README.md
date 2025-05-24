# ToDo Application

Простое приложение ToDo, написанное на Spring Boot с использованием PostgreSQL. Реализована базовая работа с задачами через REST API.

## Описание

Проект позволяет создавать, получать, редактировать и удалять задачи. Используются стандартные компоненты Spring:
- Spring Web — для создания REST-контроллеров
- Spring Data JPA — для работы с базой данных
- PostgreSQL — в качестве базы данных
- Lombok — для сокращения шаблонного кода
- Jakarta Validation — для валидации входных данных
- Spring Boot Test — для тестирования

## Зависимости

- Java 21
- Maven 3.8+
- PostgreSQL (подойдет любой PostgreSQL, нужно создать базу и указать параметры подключения в `application.properties`)

## Запуск проекта

1. Установите PostgreSQL и создайте базу данных, например `todo_db`.

2. В `src/main/resources/application.properties` укажите настройки подключения к вашей базе данных:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=ваш_пользователь
spring.datasource.password=ваш_пароль
spring.jpa.hibernate.ddl-auto=update
Соберите и запустите проект:

bash
Copy
Edit
mvn clean install
mvn spring-boot:run
Приложение будет доступно по адресу: http://localhost:8080

Тесты и проверки
Для запуска тестов:

bash
Copy
Edit
mvn test
Для генерации отчета покрытия (если подключен JaCoCo):

bash
Copy
Edit
mvn jacoco:report
Для запуска проверки стиля кода (если подключен Checkstyle):

bash
Copy
Edit
mvn checkstyle:check