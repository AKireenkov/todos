package ru.todos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс - модель для преобразования объекта из JSON в POJO и наоборот.
 */
@Data
@Builder
@AllArgsConstructor
public class Task {

    private int id;
    private String text;
    private boolean completed;

}
