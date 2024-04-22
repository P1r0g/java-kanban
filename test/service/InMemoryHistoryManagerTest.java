package service;

import org.junit.jupiter.api.Test;
import model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    final HistoryManager historyManager = new InMemoryHistoryManager();
    final Task task = new Task("Новая задача", Status.NEW, "описание");
    @Test
    void getHistory() {
    }

    @Test
    void addInHistory() {
        historyManager.addInHistory(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}