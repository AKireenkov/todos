package ru.todos.steps;

import io.restassured.response.ValidatableResponse;
import ru.todos.model.Task;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * В классе описаны базовые методы, для сокращения количества кода в тестах.
 *
 * @param <T>
 */
public class BaseSteps<T> {

    /**
     * Метод POST, принимает как аргумент тело запроса, в любом формате.
     *
     * @param body тело запроса (Например JSON или Object)
     * @param <T>  тип входного параметра.
     * @return ValidatableResponse
     */
    public static <T> ValidatableResponse postTask(T body) {
        return given()
                .body(body)
                .when()
                .post()
                .then().log().all();
    }

    /**
     * Метод PUT, принимает как аргумент тело запроса, в любом формате и id объекта, который нужно изменить.
     *
     * @param body тело запроса (Например JSON или Object)
     * @param id   id изменяемого объекта
     * @param <T>  тип входного параметра.
     * @return ValidatableResponse
     */
    public static <T> ValidatableResponse putTask(T body, int id) {
        return given()
                .body(body)
                .when()
                .put(String.valueOf(id))
                .then().log().all();
    }

    /**
     * Метод DELETE. Удаляем объект по его id.
     *
     * @param id  id удаляемого объекта
     * @param <T> тип входного параметра.
     * @return ValidatableResponse
     */
    public static <T> ValidatableResponse deleteTask(int id) {
        return given()
                .when()
                .delete(String.valueOf(id))
                .then().log().all();
    }

    /**
     * Метод GET, который возвращает первый объект из списка.
     * В методе дополнительно проверяем код ответа 200.
     *
     * @return Task
     */
    public static Task getOneTask() {
        return given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Task.class)
                .stream().findFirst().get();
    }

    /**
     * Метод GET, который возвращает список задач.
     * В методе дополнительно проверяем код ответа 200.
     *
     * @return Task
     */
    public static List<Task> getListTask() {
        return given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Task.class)
                .stream().toList();
    }

    /**
     * Метод GET, который возвращает список задач. Запрос делаем с Query params, которые получаем в виде Map.
     * Позволяет нам использовать один или несколько параметров в запросе GET.
     * В методе дополнительно проверяем код ответа 200.
     *
     * @param params Query params
     * @return Task
     */
    public static List<Task> getListTaskWithParams(Map<String, Integer> params) {
        return given()
                .when()
                .params(params)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Task.class)
                .stream().toList();
    }

    /**
     * Метод для удаления всех задач после каждого теста.
     * Такой метод не реализован в текущем приложении, но добавлен, что бы показать как должно быть.
     * Это позволит генерировать в каждом тесте, новые данные, увеличивая их атомарность.
     */
    public static void deleteAll() {
        given()
                .when()
                .delete()
                .then().log().all();
    }
}
