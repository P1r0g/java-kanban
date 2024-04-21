package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager { ;

    int generateID();

    ArrayList<Task> getAllTasks();

    void deleteAllTasks();

    Task getTask(int id);

    Task createTask(Task task);

    void updateTask(Task task);

    void delete(int id);

    ArrayList<Epic> getAllEpic();

    void deleteAllEpic();

    Epic getEpic(int id);

    Epic createEpic(Epic epic);

    void updateEpic(Epic epic);

    void deleteEpic(int id);

    List<SubTask> getSubTasksInEpic(Epic epic);

    ArrayList<SubTask> getAllSubTask();

    void deleteAllSubTask();

    SubTask getSubTask(int id);

    SubTask createSubTask(SubTask subTask);

    void updateSubTask(SubTask subTask);

    void deleteSubTask(int id);

        List<Integer> getHistory();
}
