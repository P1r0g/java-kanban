package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static model.Status.*;
import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    abstract T createTaskManager();

    @BeforeEach
    void setup() {
        taskManager = createTaskManager();
    }

    @Test
    void shouldCalculateEpicEndTimeCorrectly() {
        Epic epic = new Epic("Test Epic", NEW, "Description", Duration.ZERO, LocalDateTime.now());
        taskManager.createEpic(epic);

        LocalDateTime now = LocalDateTime.now();
        SubTask subTask1 = new SubTask("SubTask 1", NEW, "Description", epic, TaskType.SUBTASK, Duration.ofMinutes(30), now);
        SubTask subTask2 = new SubTask("SubTask 2", NEW, "Description", epic, TaskType.SUBTASK, Duration.ofMinutes(45), now.plusMinutes(30));

        taskManager.createSubTask(subTask1);
        taskManager.createSubTask(subTask2);

        assertEquals(now.plusMinutes(75), epic.getEndTime());
    }


    @Test
    void shouldDetectTaskTimeConflicts() {
        LocalDateTime now = LocalDateTime.now();
        taskManager.createTask(new Task("Task 1", NEW, "Description", Duration.ofMinutes(60), now));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskManager.createTask(new Task("Task 2", NEW, "Description", Duration.ofMinutes(30), now.plusMinutes(30)));
        });

        assertEquals("Задача уже идет", exception.getMessage(), "Incorrect exception message.");
    }

    @Test
    void shouldSortTasksByPriority() {
        LocalDateTime now = LocalDateTime.now();
        Task task1 = taskManager.createTask(new Task("Task 1", NEW, "Description", Duration.ofMinutes(30), now.plusHours(2)));
        Task task2 = taskManager.createTask(new Task("Task 2", NEW, "Description", Duration.ofMinutes(30), now.plusHours(1)));
        Task task3 = taskManager.createTask(new Task("Task 3", NEW, "Description", Duration.ofMinutes(30), now.plusHours(3)));

        List<Task> prioritizedTasks = taskManager.getPrioritizedTasks().stream().toList();

        assertEquals(task2, prioritizedTasks.get(0), "Task priority is incorrect.");
        assertEquals(task1, prioritizedTasks.get(1), "Task priority is incorrect.");
        assertEquals(task3, prioritizedTasks.get(2), "Task priority is incorrect.");
    }

    @Test
    void shouldHandleEmptyHistoryManager() {
        List<Task> history = taskManager.getHistoryManager();

        assertNotNull(history, "HistoryManager should not be null.");
        assertTrue(history.isEmpty(), "HistoryManager should be empty.");
    }

    @Test
    void shouldHandleHistoryWithDuplicates() {
        Task task = taskManager.createTask(new Task("Task", NEW, "Description", Duration.ZERO, LocalDateTime.now()));
        taskManager.getTask(task.getId());
        taskManager.getTask(task.getId());

        List<Task> history = taskManager.getHistoryManager();

        assertEquals(1, history.size(), "HistoryManager should not allow duplicates.");
    }

    @Test
    void shouldRemoveFromHistoryCorrectly() {
        Task task1 = taskManager.createTask(new Task("Task 1", NEW, "Description", Duration.ofMinutes(30), LocalDateTime.now()));
        Task task2 = taskManager.createTask(new Task("Task 2", NEW, "Description", Duration.ofMinutes(60), LocalDateTime.now().plusHours(1)));
        Task task3 = taskManager.createTask(new Task("Task 3", NEW, "Description", Duration.ofMinutes(40), LocalDateTime.now().plusHours(3)));

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getTask(task3.getId());

        taskManager.delete(task2.getId());

        List<Task> history = taskManager.getHistoryManager();

        assertEquals(2, history.size(), "HistoryManager should correctly update after task removal.");
        assertFalse(history.contains(task2), "Removed task should not be in history.");
    }
}

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    InMemoryTaskManager createTaskManager() {
        return new InMemoryTaskManager();
    }
}

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @Override
    FileBackedTaskManager createTaskManager() {
        return new FileBackedTaskManager(new File("C:\\Users\\pirog\\java-kanban\\test\\testfile.txt"));
    }
}
