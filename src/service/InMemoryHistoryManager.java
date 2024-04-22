package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>(10);

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public <T extends Task> void addInHistory(T task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(task);
    }
}
