package ru.todos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.todos.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static ru.todos.config.Config.installSpec;
import static ru.todos.steps.BaseSteps.*;

public class GetTask {

    @Before
    public void before() {
        installSpec();
    }

    @After
    public void after() {
        deleteAll();
    }

    @Test
    public void getTaskAndCheckCode() {
        Task task1 = new Task(1, "task", true);
        postTask(task1);

        Task resultTask = getOneTask();

        assertEquals(task1, resultTask);
    }


    @Test
    public void getListTasks() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", true);
        Task task3 = new Task(3, "task3", false);
        List<Task> tasks = List.of(task1, task2, task3);

        postTask(task1);
        postTask(task2);
        postTask(task3);
        List<Task> resultList = getListTask();

        assertEquals(resultList, tasks);
    }

    @Test
    public void getEmptyList() {
        List<Task> emptyList = new ArrayList<>();
        List<Task> resultList = getListTask();

        assertEquals(emptyList, resultList);
        assertEquals(0, resultList.size());
    }

    @Test
    public void getListTasksWithOffset() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", false);
        Task task3 = new Task(3, "task3", false);
        Task task4 = new Task(4, "task4", true);
        List<Task> tasks = List.of(task3, task4);
        Map<String, Integer> params = Map.of(
                "offset", 2
        );

        postTask(task1);
        postTask(task2);
        postTask(task3);
        postTask(task4);
        List<Task> resultList = getListTaskWithParams(params);

        assertEquals(resultList, tasks);
    }

    @Test
    public void getListTasksWithLimit() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", false);
        Task task3 = new Task(3, "task3", false);
        Task task4 = new Task(4, "task4", true);
        List<Task> tasks = List.of(task1, task2);
        Map<String, Integer> params = Map.of(
                "limit", 2
        );

        postTask(task1);
        postTask(task2);
        postTask(task3);
        postTask(task4);
        List<Task> resultList = getListTaskWithParams(params);

        assertEquals(resultList, tasks);
    }

    @Test
    public void getListTasksWithOffsetAndLimit() {
        Task task1 = new Task(1, "task", true);
        Task task2 = new Task(2, "task2", false);
        Task task3 = new Task(3, "task3", false);
        Task task4 = new Task(4, "task4", true);
        List<Task> tasks = List.of(task3);
        Map<String, Integer> params = Map.of(
                "offset", 2,
                "limit", 1
        );

        postTask(task1);
        postTask(task2);
        postTask(task3);
        postTask(task4);
        List<Task> resultList = getListTaskWithParams(params);

        assertEquals(resultList, tasks);
    }
}
