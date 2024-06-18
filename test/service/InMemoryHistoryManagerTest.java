package service;

import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    final HistoryManager historyManager = new InMemoryHistoryManager();
    final Task task = new Task(1,"Новая задача", Status.NEW, "описание");
    final Task task1 = new Task(2,"Новая задача", Status.NEW, "000писание");
//    @Test
//    void getHistory() {
//    }

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
        historyManager.remove(task.getId());
        List<Task> tasks = historyManager.getAll();
        assertEquals(tasks, List.of(task1), "Ошибка");
    }
}