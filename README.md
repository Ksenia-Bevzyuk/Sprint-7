
# Sprint-7

Тестирование API учебного сервиса Яндекс.Самокат


## Documentation

[Documentation](https://qa-scooter.praktikum-services.ru/docs/)


## Technologies
- Java 11

#### Properties
- maven 11
- aspectj 1.9.7
- allure 2.15.0

#### Dependencies
- junit:junit:4.13.2(test)
- io.rest-assured:rest-assured:4.4.0
- com.google.code.gson:gson:2.8.9
- com.fasterxml.jackson.core:jackson-databind:2.13.4.2
- io.qameta.allure:allure-junit4:2.15.0
- io.qameta.allure:allure-rest-assured:2.15.0
- com.github.javafaker:javafaker:1.0.2

#### Plugins
- maven-surefire-plugin 2.22.2
- allure-maven 2.10.0


## Running Tests

To run tests, run the following command

```bash
  mvn clean test
```

