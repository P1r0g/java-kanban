package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    final HistoryManager historyManager = new InMemoryHistoryManager();
    final Task task = new Task(1,"Новая задача", Status.NEW, "описание");
    final Task task1 = new Task(2,"Новая задача", Status.NEW, "000писание");
    final Epic epic = new Epic(3, "НОвый эпик", Status.NEW, "ООписание0");
    final SubTask subTask = new SubTask(4, "Новая подзадача", Status.NEW, "Описание");
    final SubTask subTask1 = new SubTask(5, "New подзадача", Status.NEW, "О00писание");

    @Test
    void addInHistory() {
        historyManager.add(task);
        historyManager.add(task1);
        List<Task> history = historyManager.getAll();
        assertEquals(history, List.of(task, task1), "Ошибка");
    }

    @Test
    void removeInHistory() {
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(epic);
        historyManager.add(subTask);
        historyManager.add(subTask1);
        historyManager.remove(task.getId());
        historyManager.remove(subTask.getId());
        List<Task> tasks = historyManager.getAll();
        assertEquals(tasks, List.of(task1, epic, subTask1), "Ошибка");
    }
}