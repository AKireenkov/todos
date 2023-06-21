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

public class PutTask {

    @Before
    public void before() {
        installSpec();
    }

    @After
    public void after() {
        deleteAll();
    }

    @Test
    public void putForExistTask() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(1, "newTask", false);
        postTask(task1);
        putTask(task2, task1.getId());

        Task expectedTask = getOneTask();

        assertEquals(expectedTask, task2);
    }

    @Test
    public void putForListTask() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(1, "newTask", false);
        Task task3 = new Task(3, "task3", true);
        List<Task> tasks = List.of(task3, task2);

        postTask(task1);
        postTask(task3);
        putTask(task2, task1.getId());

        List<Task> expectedTask = getListTask();

        assertEquals(expectedTask, tasks);
    }

    @Test
    public void putForDoNotExistTask() {
        Task task2 = new Task(1, "newTask", false);
        var putResp = putTask(task2, task2.getId());
        putResp.statusCode(404);

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
}
