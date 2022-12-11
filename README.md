![jwt](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

# Трекер привычек

1. Создать базовую архитектуру приложения. Реализовать сущности и репозиторий для CRUD операций
    1. UserEntity
    2. Habit  
    3. HabitList
2. Добавить авторизацию/аутентификацию/регистрацию (spring security)
3. Реализовать api для взаимодействия с системой + добавить swagger 
4. Клиент
5. Деплой

![схема запросов](https://sun9-88.userapi.com/impg/qKCBFcNbPy9bFTHYUo46jkP1PFZSUrkV5KQiug/rYUOznOjPRY.jpg?size=1280x599&quality=96&sign=dddc9a98260353489150229dbc5c5a2b&type=album)

# Конфигурация приложения

## Путь к базе данных

Пример:
```properties
spring.datasource.url=jdbc:postgresql://localhost/habit_tracker
```

## Логин и пароль для базы данных

Пример:
```properties
spring.datasource.username=login
spring.datasource.password=password
```

## Описание стратегии работы с бд при перезапуске приложения

```properties
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
```

## Секрет JWT

```properties
app.jwt.secret=veryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecret
```

## Время жизни токена JWT

```properties
app.jwt.expire=600000000000
```

# PMD

Как запустить PMD?
1) Идем в Edit configurations...
2) Добавить новую Maven конфигурацию
3) В поле Run пишем "pmd:pmd"
4) При запуске этой конфигурации получаем отчет. Его можно найти по пути **/target/site/pmd.html
