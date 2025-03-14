API для управления учебными курсами

Этот проект представляет собой REST API для управления учебными курсами. Он поддерживает CRUD-операции для курсов и студентов, отправку
email-уведомлений и JWT-аутентификацию с ролевым доступом.

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
К проекту подключен swagger после локального запуска проекта для получения информации о ендпоинтах
переходим → http://localhost:8080/swagger-ui.html

Стек технологий

- Java 17+, Spring Boot, Spring Security
- JWT, PostgreSQL/H2
- Lombok, Swagger (Springdoc OpenAPI)

Использование:
Есть открытые ендпоинты для регистрации пользователей с ролями ADMIN, USER
пример запроса на создание пользователя с ролью admin:

curl --location 'http://localhost:8080/auth/register-admin' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=908F6F45FEE87998407A39516F51C917' \
--data-raw '{
"request_id" : "12345",
"email": "test3@gmail.com",
"first_name" : "Altynbek",
"last_name": "Umbetbayev",
"password": "test"
}'

в ответ мы получаем jwt токен:

{
"success":true,
"message":"
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0M0BnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NDE5NDIzNDEsImV4cCI6MTc0MTk3ODM0MX0.8HgzDhAIho9m1B5lTclGKirgsG-OhEuCdEmvvVrgYHz_N6qXm8YWCJPZd9DL3F-ZH15azQFQJck5zHPQM7m-7A",
"error_code":0
}

в дальнейшем когда делаем запросы на другие ендпониты где нужны авторизационные данные, то в header запроса добавляем наш токен по ключу
Authorization и значние Baerer [ваш полученный jwt token]

пример запроса на ендпоинт где нужна авторизация

curl --location 'http://localhost:8080/course/create' \
--header 'Authorization: Bearer
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0M0BnbWFpbC5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NDE5NDIzNDEsImV4cCI6MTc0MTk3ODM0MX0.8HgzDhAIho9m1B5lTclGKirgsG-OhEuCdEmvvVrgYHz_N6qXm8YWCJPZd9DL3F-ZH15azQFQJck5zHPQM7m-7A' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=908F6F45FEE87998407A39516F51C917' \
--data '{
"request_id": "test",
"course_name": "математика",
"start_date": "2025-04-01",
"end_date": "2025-06-30"
}'

