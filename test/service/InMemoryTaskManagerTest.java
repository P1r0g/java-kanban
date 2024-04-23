package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Status.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Managers.getDefault();

    @Test
    void addNewTask() {
        Task task = taskManager.createTask(new Task("Test addNewTask", NEW, "Test addNewTask description"));

        final int taskId = task.getId();

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldGetTasks() {
        Task task = taskManager.createTask(new Task("Task1", NEW, "Описание 1"));
        assertNotNull(taskManager.getAllTasks(), "Ничего нет");
        assertEquals(task, taskManager.getTask(1), "Они не равны");
    }

    @Test
    void shouldGetEpicAndSubTask() {
        Epic epic = taskManager.createEpic(new Epic("задача", NEW, "Описание 1"));
        SubTask subtask = taskManager.createSubTask(new SubTask("подзадача", NEW, "Описание", epic));
        assertNotNull(taskManager.getAllEpic(), "Эпики пусты");
        assertNotNull(taskManager.getAllSubTask(), "подзадачи пусты");
        assertEquals(epic, taskManager.getEpic(epic.getId()), "Они не равны");
        assertEquals(subtask, taskManager.getSubTask(subtask.getId()), "Они не равны");
    }

    @Test
    void shouldAddEpicInSubTaskList() {
        Epic epic = new Epic("Эпик", NEW, "Описание");

        assertNotEquals(0, epic.getSubTasks().size(), "Epic добавляется");
    }

    @Test
    void shouldCreateSubTaskInEpic() {

    }

    @Test
    void shouldCreateTaskManagerAndHistoryManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager не создан");
        assertNotNull(taskManager.getHistoryManager(), "HistoryManager не создан");
    }

    @Test
    void shouldCreateAnyTasks() {
        Task task = taskManager.createTask(new Task("Task1", NEW, "Описание 1"));
        Epic epic = taskManager.createEpic(new Epic("Task1", NEW, "Описание 1"));
        SubTask subTask = taskManager.createSubTask(new SubTask("Task1", NEW, "Описание 1", epic));

        Task task2 = taskManager.getTask(task.getId());
        Epic epic2 = taskManager.getEpic(epic.getId());
        SubTask subTask2 = taskManager.getSubTask(subTask.getId());

        assertNotEquals(task.getId(), epic.getId(), "ID не совпадают");
        assertNotEquals(task.getId(), subTask.getId(), "ID не совпадают");
        assertNotEquals(epic.getId(), subTask.getId(), "ID не совпадают");
        assertEquals(task, task2, "Task находится");
        assertEquals(epic, epic2, "Epic находится");
        assertEquals(subTask, subTask2, "SubTask находится");
    }

    @Test
    void shouldConflictTasksById() {
        Task task = taskManager.createTask(new Task("Task", NEW, "Description"));
        Task task2 = taskManager.createTask(new Task(1, "Task", NEW, "Description"));

        List<Task> all = taskManager.getAllTasks();

        assertNotEquals(1, all.size(), "Конфликт задач");
        assertEquals(task2, all.get(1));
    }

    @Test
    void shouldImmutabilityTask() {
        Task task = new Task("Task", NEW, "Description");
        Task task2 = taskManager.createTask(task);

        assertEquals(task, task2, "Task изменяется");
    }

    @Test
    void shouldAddHistoryManager() {
        Task task = taskManager.createTask(new Task("Task", NEW, "Description"));
        Epic epic = taskManager.createEpic(new Epic("Epic", NEW, "Description"));

        List<Task> getTasks = new ArrayList<>();
        getTasks.add(taskManager.getTask(task.getId()));
        getTasks.add(taskManager.getEpic(epic.getId()));
        List<Task> historyManager = taskManager.getHistoryManager();

        for (int i = 0; i < getTasks.size(); i++){
            assertEquals(getTasks.get(i), historyManager.get(i), "Не равны");
        }
    }
}