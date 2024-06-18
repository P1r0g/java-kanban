package service;

import model.Task;
import java.util.List;

public interface HistoryManager {

    <T extends Task> void add(Task task);
    List<Task> getAll();
    void remove(int id);

}
