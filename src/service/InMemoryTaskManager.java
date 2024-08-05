package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Epic> epics;
    protected HashMap<Integer, SubTask> subTasks;
    private int id;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    private int generateID() {
        return ++id;
    }

    @Override
    public List<Task> getHistoryManager() {
        return historyManager.getAll();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
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
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpic() {
        for (Epic epic : epics.values()) {
            for (SubTask subTask : epic.getSubTasks()) {
                historyManager.remove(subTask.getId());
            }
            historyManager.remove(epic.getId());
        }
        subTasks.clear();
        epics.clear();
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addInHistory(epics.get(id));
        for (SubTask subTask : epics.get(id).getSubTasks()) {
            historyManager.addInHistory(subTask);
        }
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
            historyManager.remove(subTask.getId());
            subTasks.remove(subTask.getId());
        }
        historyManager.remove(id);
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
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            epic.updateStatus(epic);
        }
        subTasks.clear();
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

            historyManager.remove(id);
            subTasks.remove(id);
        }
    }
}
