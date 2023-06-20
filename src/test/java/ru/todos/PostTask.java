package ru.todos;

import org.junit.Before;
import org.junit.Test;
import ru.todos.model.Task;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.todos.config.Config.installSpec;
import static ru.todos.steps.BaseSteps.*;

public class PostTask {

    @Before
    public void before() {
        installSpec();
    }

    @Before
    public void after() {
        deleteAll();
    }

    @Test
    public void createTaskAndCheckOKCode() {
        Task task1 = new Task(1, "task", true);
        postTask(task1)
                .statusCode(201);
    }

    @Test
    public void createTwoTasksAndCheckList() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", true);
        postTask(task1);
        postTask(task2);

        List<Task> tasks = getListTask();
        assertTrue(tasks.containsAll(List.of(task1, task2)));
    }

    @Test
    public void createTaskWithExistingId() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(1, "task2", false);
        postTask(task1);
        postTask(task2);

        List<Task> tasks = getListTask();

        assertEquals(1, tasks.size());
        assertTrue(tasks.contains(task1));
    }

    @Test
    public void errorWhenCreateTaskWithoutId() {
        String body = """             
                     {
                        "text": "task",
                        "completed": true
                     }                 
                """;
        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: missing field `id`";
        assertTrue(response.contains(text));
    }


    @Test
    public void errorWhenCreateTaskWithoutText() {
        String body = """             
                     {
                        "id": 1,
                        "completed": true
                     }     
                """;

        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: missing field `text`";
        assertTrue(response.contains(text));
    }

    @Test
    public void errorWhenCreateTaskWithoutCompleted() {
        String body = """             
                     {
                        "id": 1,
                        "text": "task"
                     }     
                """;

        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: missing field `completed`";
        assertTrue(response.contains(text));
    }

    @Test
    public void errorWhenCreateTaskWithoutParams() {
        String body = """             
                     {
                     
                     }     
                """;

        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: missing field `id`";
        assertTrue(response.contains(text));
    }

    @Test
    public void errorWhenCreateTaskWithIncorrectFieldTypeOne() {
        String body = """             
                     {
                        "id": 1,
                        "text": false,
                        "completed": true
                     }                 
                """;
        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        System.out.println(response);
        String text = "Request body deserialize error: invalid type: boolean `false`, expected a string";
        assertTrue(response.contains(text));
    }

    @Test
    public void errorWhenCreateTaskWithIncorrectFieldTypeTwo() {
        String body = """             
                     {
                        "id": 1,
                        "text": "test",
                        "completed": "true"
                     }                 
                """;
        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: invalid type: string \"true\", expected a boolean";
        assertTrue(response.contains(text));
    }

    @Test
    public void errorWhenCreateTaskWithoutValue() {
        String body = """             
                     {
                        "id": 1000,
                        "text": "test",
                        "completed":
                     }                 
                """;
        String response = postTask(body)
                .statusCode(400)
                .extract()
                .asString();
        String text = "Request body deserialize error: expected value";
        assertTrue(response.contains(text));
    }

}
