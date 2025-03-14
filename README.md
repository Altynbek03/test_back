API для управления учебными курсами

Этот проект представляет собой REST API для управления учебными курсами. Он поддерживает CRUD-операции для курсов и студентов, отправку email-уведомлений и JWT-аутентификацию с ролевым доступом.

Функции
- CRUD для курсов и студентов
- Регистрация студентов на курс (email-уведомления)
- JWT-аутентификация (ADMIN, USER)
- Swagger UI для тестирования API

Для запуска необходимо клонировать гит и открыть в idea
Intelliji idea:
- в файле application.yaml указываем свою базу данных для того чтобы локально поднять проект
  spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/course_db -- указываем хост базы данных
    username: postgres -- пишем логин
    password: yourpassword -- пишем пароль
  jpa:
    hibernate:
      ddl-auto: update


API-эндпоинты 
К проекту подключен swagger после локального запуска проекта для получения информации о ендпоинтах переходим → http://localhost:8080/swagger-ui.html 

Стек технологий
- Java 17+, Spring Boot, Spring Security
- JWT, PostgreSQL/H2
- Lombok, Swagger (Springdoc OpenAPI)
