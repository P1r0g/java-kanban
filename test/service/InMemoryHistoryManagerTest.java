package service;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    final HistoryManager historyManager = new InMemoryHistoryManager();

    final LocalDateTime now = LocalDateTime.now();
    final Task task = new Task(1, "Новая задача", Status.NEW, "описание", Duration.ofMinutes(30), now);
    final Task task1 = new Task(2, "Новая задача", Status.NEW, "000писание", Duration.ofMinutes(45), now.plusMinutes(30));
    final Epic epic = new Epic(3, "НОвый эпик", Status.NEW, "ООписание0", Duration.ZERO, now.plusHours(3));
    final SubTask subTask = new SubTask(4, "Новая подзадача", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(60), now);
    final SubTask subTask1 = new SubTask(5, "New подзадача", Status.NEW, "О00писание", epic, TaskType.SUBTASK, Duration.ofMinutes(30), now.plusMinutes(90));

    @Test
    void addInHistory() {
        historyManager.addInHistory(task);
        historyManager.addInHistory(task1);
        List<Task> history = historyManager.getAll();
        assertEquals(history, List.of(task, task1), "Ошибка");
    }

    @Test
    void removeInHistory() {
        historyManager.addInHistory(task);
        historyManager.addInHistory(task1);
        historyManager.addInHistory(epic);
        historyManager.addInHistory(subTask);
        historyManager.addInHistory(subTask1);

        historyManager.remove(task.getId());
        historyManager.remove(subTask.getId());

        List<Task> tasks = historyManager.getAll();
        assertEquals(tasks, List.of(task1, epic, subTask1), "Ошибка");
    }
}
