package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int id;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = historyManager;
    }
    private int generateID() {
        return ++id;
    }

    public List<Task> getTasks() {
        return (List<Task>) tasks.values();
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public List<Task> getHistoryManager() {
        return historyManager.getHistory();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.addInHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) throw new RuntimeException("Такой задачи нет");
        tasks.put(task.getId(), task);
    }

    @Override
    public void delete(int id) {
        if (!tasks.containsKey(id)) {
            throw new RuntimeException("Такой задачи нет(");
        }

        tasks.remove(id);
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpic() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addInHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateID());
        epics.put(epic.getId(), epic);
        return epic;
    }


    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        epic.updateStatus(saved);
    }

    @Override
    public void deleteEpic(int id) {
        if (!epics.containsKey(id)) throw new RuntimeException("Такого эпика не существует");
        Epic epic = epics.get(id);
        for (SubTask subTask : epic.getSubTasks()) {
            subTasks.remove(subTask.getId());
        }
        epics.remove(id);
    }

    @Override
    public List<SubTask> getSubTasksInEpic(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTask() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            epic.updateStatus(epic);
        }

    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.addInHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateID());
        subTasks.put(subTask.getId(), subTask);

        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addTask(subTask);
        epic.updateStatus(epic);
        return subTask;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);

        Epic epic = subTask.getEpic();
        Epic epicUpdates = epics.get(epic.getId());

        for (int i = 0; i < epicUpdates.getSubTasks().size(); i++) {
            if (epicUpdates.getSubTasks().get(i).equals(subTask)) {
                epicUpdates.getSubTasks().set(i, subTask);
            }
        }
        epic.updateStatus(epicUpdates);
    }

    @Override
    public void deleteSubTask(int id) {
        if (!subTasks.containsKey(id)) throw new RuntimeException("Такой подзадачи нет");
        else {
            SubTask removeSubTask = subTasks.remove(id);

            Epic epic = removeSubTask.getEpic();
            Epic epicSaved = epics.get(epic.getId());

            epicSaved.getSubTasks().remove(removeSubTask);
            epic.updateStatus(epicSaved);

            subTasks.remove(id);
        }
    }
}
