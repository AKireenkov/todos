# toDo

В этом репозитории хранятся автотесты REST API, написанные для приложения ToDo

**Для запуска проекта, необходимо наличие следующего ПО:**  
- Java 17
- Maven 3.8
- Docker

**Запус проекта:**
- В директории tester-task выполнить команду 

```
docker load --input todo-app.tar
```

- Там же, выполнить команду на запуск контейнера

```
docker run -p 8080:4242 todo-app
```

# **REST - запросы**

_**Basic authorization - admin:admin**_

GET http://localhost:8080/todos

**Получить список задач**

Ответ:
```
[
    {
        "id": 1,
        "text": "Задача 1",
        "completed": true
    },
    {
        "id": 2,
        "text": "Задача 2",
        "completed": false
    }
]
```

POST http://localhost:8080/todos/

**Добавить задачу**

Пример запроса:
```
{
    "id" : 5,
    "text" : "Задача 1",
    "completed" : false
}
```


PUT http://localhost:8080/todos/:id

**Изменить задачу**

_Id_ - номер задачи, которую нужно изменить


Пример запроса:
```
{
    "id" : 5,
    "text" : "Задача 1",
    "completed" : false
}
```

DELETE http://localhost:8080/todos/:id

**Изменить задачу**

_Id_ - номер задачи, которую нужно удалить
