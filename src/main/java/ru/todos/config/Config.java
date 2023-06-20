package ru.todos.config;

import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.requestSpecification;

public class Config {
    public static final String URL = "http://localhost:8080/todos";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    /**
     * Метод для конфигурирования запроса, в котором устанавливаем базовый URL,
     * тип контента - JSON, и метод авторизации - Basic Auth.
     *
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .setAuth(basicAuth())
                .build();
    }

    /**
     * Устанавливаем непосредственно в RequestSpecification результат соответствующего метода.
     */
    public static void installSpec() {
        requestSpecification = requestSpec();
    }

    /**
     * Реализация Basic Auth схемы, где устанавливается логин и пароль для авторизации.
     *
     * @return BasicAuthScheme
     */
    private static BasicAuthScheme basicAuth() {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setUserName(USERNAME);
        basicAuthScheme.setPassword(PASSWORD);
        return basicAuthScheme;
    }
}
