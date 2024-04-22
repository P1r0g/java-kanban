package service;

import model.*;
import service.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Status.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager = Managers.getDefault();
    @Test
    void getHistoryManager() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void deleteAllTasks() {
    }

    @Test
    void getTask() {
    }

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
    void updateTask() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllEpic() {
    }

    @Test
    void deleteAllEpic() {
    }

    @Test
    void getEpic() {
    }

    @Test
    void createEpic() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void deleteEpic() {
    }

    @Test
    void getSubTasksInEpic() {
    }

    @Test
    void getAllSubTask() {
    }

    @Test
    void deleteAllSubTask() {
    }

    @Test
    void getSubTask() {
    }

    @Test
    void createSubTask() {
    }

    @Test
    void updateSubTask() {
    }

    @Test
    void deleteSubTask() {
    }
}