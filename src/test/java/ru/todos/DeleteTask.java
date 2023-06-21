package ru.todos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.todos.model.Task;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.todos.config.Config.installSpec;
import static ru.todos.steps.BaseSteps.*;

public class DeleteTask {

    @Before
    public void before() {
        installSpec();
    }

    @After
    public void after() {
        deleteAll();
    }

    @Test
    public void deleteExistTask() {
        Task task1 = new Task(1, "task", true);
        deleteTask(task1.getId());

        var response = given()
                .when()
                .get()
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Task.class)
                .stream().findFirst();

        assertTrue(response.isEmpty());
    }

    @Test
    public void deleteDoNotExistTask() {
        Task task1 = new Task(1, "task", true);
        deleteTask(task1.getId()).statusCode(404);
    }

    @Test
    public void deleteFromListTask() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", false);
        Task task3 = new Task(3, "task3", true);
        List<Task> expectedTask = List.of(task1, task3);

        postTask(task1);
        postTask(task2);
        postTask(task3);
        deleteTask(task1.getId());

        List<Task> tasks = getListTask();

        assertEquals(expectedTask, tasks);
    }
}
