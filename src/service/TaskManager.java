package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int id;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private int generateID() {
        return ++id;
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }

    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) throw new RuntimeException("Такой задачи нет");
        tasks.put(task.getId(), task);
    }

    public void delete(int id) {
        if (!tasks.containsKey(id)){
            throw new RuntimeException("Такой задачи нет(");
        }

        tasks.remove(id);
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpic() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        if (saved == null) {
            return;
        }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        epic.updateStatus(saved);
    }

    public void deleteEpic(int id) {
        if (!epics.containsKey(id)) throw new RuntimeException("Такого эпика не существует");
        else {
            Epic epic = epics.get(id);
            epic.getSubTasks().clear();
            epics.remove(id);
        }
    }

    public List<SubTask> getSubTasksInEpic(Epic epic) {
        return epic.getSubTasks();
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAllSubTask() {
        subTasks.clear();
        for (Epic epic : epics.values()){
            epic.getSubTasks().clear();
        }
    }

    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateID());
        subTasks.put(subTask.getId(), subTask);

        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addTask(subTask);
        epic.updateStatus(epic);

        return subTask;
    }

    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);

        Epic epic = subTask.getEpic();
        Epic epicUpdates = epics.get(epic.getId());

        epicUpdates.getSubTasks().set(subTask.getId(), subTask);
        epic.updateStatus(epicUpdates);
    }

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
