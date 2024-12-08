package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Epic> epics;
    protected HashMap<Integer, SubTask> subTasks;
    private int id;
    protected final HistoryManager historyManager;
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator
            .nullsLast(Comparator.naturalOrder())));

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    private boolean isOverlapping(Task newTask) {
        return prioritizedTasks.stream().anyMatch(existingTask -> doTasksOverlap(existingTask, newTask));
    }

    private boolean doTasksOverlap(Task t1, Task t2) {
        LocalDateTime start1 = t1.getStartTime();
        LocalDateTime end1 = t1.getEndTime();
        LocalDateTime start2 = t2.getStartTime();
        LocalDateTime end2 = t2.getEndTime();

        if (start1 == null || start2 == null || end1 == null || end2 == null) {
            return false;
        }
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    private int generateID() {
        return ++id;
    }

    private void updatePrioritizedTasks(Task task) {
        prioritizedTasks.remove(task);
        if (task.getStartTime() != null) {
            if (isOverlapping(task)) {
                throw new RuntimeException("Новая задача пересекается с уже существующими задачами.");
            }
            prioritizedTasks.add(task);
        }
    }

    private void recalculateEpicTiming(Epic epic) {
        if (epic.getSubTasks().isEmpty()) {
            epic.setStartTime(null);
            epic.setDuration(null);
            return;
        }

        LocalDateTime earliestStart = null;
        LocalDateTime latestEnd = null;
        long totalDuration = 0;

        for (SubTask subTask : epic.getSubTasks()) {
            if (subTask.getStartTime() != null) {
                if (earliestStart == null || subTask.getStartTime().isBefore(earliestStart)) {
                    earliestStart = subTask.getStartTime();
                }
                if (latestEnd == null || subTask.getEndTime().isAfter(latestEnd)) {
                    latestEnd = subTask.getEndTime();
                }
                totalDuration += subTask.getDuration().toMinutes();
            }
        }

        epic.setStartTime(earliestStart);
        epic.setDuration(Duration.ofMinutes(totalDuration));
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
        tasks.values().forEach(task -> {
            historyManager.remove(task.getId());
            prioritizedTasks.remove(task);
        });
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
        updatePrioritizedTasks(task);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) throw new RuntimeException("Такой задачи нет");
        updatePrioritizedTasks(task);
        tasks.put(task.getId(), task);
    }

    @Override
    public void delete(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask == null) throw new RuntimeException("Такой задачи нет");
        historyManager.remove(id);
        prioritizedTasks.remove(removedTask);
    }

    @Override
    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpic() {
        epics.values().forEach(epic -> {
            epic.getSubTasks().forEach(subTask -> {
                historyManager.remove(subTask.getId());
                prioritizedTasks.remove(subTask);
            });
            historyManager.remove(epic.getId());
        });
        subTasks.clear();
        epics.clear();
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
        recalculateEpicTiming(epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epics.containsKey(epic.getId())) throw new RuntimeException("Такого эпика нет");
        epics.put(epic.getId(), epic);
        recalculateEpicTiming(epic);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) throw new RuntimeException("Такого эпика нет");
        epic.getSubTasks().forEach(subTask -> {
            subTasks.remove(subTask.getId());
            historyManager.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
        });
        historyManager.remove(id);
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
        subTasks.values().stream()
                .map(SubTask::getId)
                .forEach(historyManager::remove);

        epics.values().forEach(epic -> {
            epic.getSubTasks().clear();
            epic.updateStatus(epic);
        });

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
        updatePrioritizedTasks(subTask);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpic().getId());
        epic.addTask(subTask);
        recalculateEpicTiming(epic);
        return subTask;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        updatePrioritizedTasks(subTask);
        subTasks.put(subTask.getId(), subTask);
        Epic epic = subTask.getEpic();
        recalculateEpicTiming(epic);
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask subTask = subTasks.remove(id);
        if (subTask == null) throw new RuntimeException("Такой подзадачи нет");
        Epic epic = subTask.getEpic();
        epic.getSubTasks().remove(subTask);
        recalculateEpicTiming(epic);
        historyManager.remove(id);
        prioritizedTasks.remove(subTask);
    }
}
