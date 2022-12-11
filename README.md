![jwt](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

# Habit Tracker

## Задачи

1. Создать базовую архитектуру приложения. Реализовать сущности и репозиторий для CRUD операций
   1. UserEntity
   2. Habit
   3. HabitList
2. Добавить авторизацию/аутентификацию/регистрацию (spring security)
3. Реализовать api для взаимодействия с системой + добавить swagger
4. Клиент
5. Деплой

![схема запросов](https://sun9-88.userapi.com/impg/qKCBFcNbPy9bFTHYUo46jkP1PFZSUrkV5KQiug/rYUOznOjPRY.jpg?size=1280x599&quality=96&sign=dddc9a98260353489150229dbc5c5a2b&type=album)

## Как использовать Swagger?

1) Переходим по ссылке: https://habit.quantumwijeeworks.ru/swagger-ui/index.html
2) Надо убедится, что в поле servers выбран наш сервер

![схема запросов](wiki/0.jpg)

3) Регистрируемся

Находим запрос на регистрацию. Раскрываем его. Нажимаем на кнопку "Try it out"

![схема запросов](wiki/1.jpg)

- Заполняем тело запроса
- Нажимаем на кнопку "Execute"
- Смотрим, что пришел ответ с кодом 200

![схема запросов](wiki/2.jpg)

4) Входим в систему

Все делаем аналогично предыдущему шагу. После выполнения запроса копируем JWT.

![схема запросов](wiki/3.jpg)

Нажимаем на кнопку "Authorize".

![схема запросов](wiki/4.jpg)

В открывшемся окне есть одно текстовое поле. В него вставляем наш JWT, который мы 
ранее скопировали. Жмем на кнопку "Authorize"

![схема запросов](wiki/5.jpg)

Все, swagger готов к работе.

## Конфигурация приложения

### Путь к базе данных

Пример:
```properties
spring.datasource.url=jdbc:postgresql://localhost/habit_tracker
```

### Логин и пароль для базы данных

Пример:
```properties
spring.datasource.username=login
spring.datasource.password=password
```

### Описание стратегии работы с бд при перезапуске приложения

```properties
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
```

### Секрет JWT

```properties
app.jwt.secret=veryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecret
```

### Время жизни токена JWT

```properties
app.jwt.expire=600000000000
```

## PMD

Как запустить PMD?
1) Идем в Edit configurations...
2) Добавить новую Maven конфигурацию
3) В поле Run пишем "pmd:pmd"
4) При запуске этой конфигурации получаем отчет. Его можно найти по пути **/target/site/pmd.html
