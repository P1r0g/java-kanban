package service;

import model.*;

import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int seq;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private int generateID() {
        return ++seq;
    }

    public void getAll() {
        System.out.println(tasks);
    }

    public void deleteAll() {
        tasks.clear();
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public Task create(Task task) {
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }

    public void update(Task task) {
        tasks.put(task.getId(), task);
    }

    public void delete(int id) {
        if (!tasks.containsValue(get(id))){
            throw new RuntimeException("Такой задачи нет(");
        }

        tasks.remove(id);
    }

    public void getAllEpic() {
        System.out.println(epics);
    }

    public void deleteAllEpic() {
        epics.clear();
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
        if (!epics.containsValue(getEpic(id))) throw new RuntimeException("Такого эпика не существует");
        else {
            Epic epic = epics.get(id);
            epic.getSubTasks().clear();
            epics.remove(id);
        }
    }

    public void getSubTasksInEpic(Epic epic) {
        System.out.println(epic.getSubTasks());
    }

    public void getAllSubTask() {
        System.out.println(subTasks);
    }

    public void deleteAllSubTask() {
        subTasks.clear();
    }

    public SubTask getSubTask(int id) {
        return subTasks.get(id);
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateID());
        subTasks.put(subTask.getId(), subTask);

        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addTask(subTask);

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
        if (!subTasks.containsValue(getSubTask(id))) throw new RuntimeException("Такой подзадачи нет");
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
