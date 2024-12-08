package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<SubTask> subTasks = new ArrayList<>();

    public Epic(int id, String name, Status status, String description){
        super(id, name, status,description);
        this.type = TaskType.EPIC;
    }

    public Epic(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        super(id, name, status, description, duration, startTime);
        this.type = TaskType.EPIC;
    }

    public Epic(String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        super(name, status, description, duration, startTime);
        this.type = TaskType.EPIC;
    }

    public Epic(String name, Status status, String description, TaskType type) {
        super(name, status, description);
        this.type = type;
    }

    public Epic(String name, Status status, String description) {
        super(name, status, description);
        this.type = TaskType.EPIC;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addTask(SubTask subTask) {
        if (this.getId() == subTask.getId()) {
            System.out.println("Нельзя добавить epic в subTaskList");
        } else {
            subTasks.add(subTask);
            recalculateFields();
        }
    }

    public void removeTask(SubTask subTask) {
        subTasks.remove(subTask);
        recalculateFields();
    }

    private void recalculateFields() {
        if (subTasks.isEmpty()) {
            setDuration(Duration.ZERO);
            setStartTime(null);
        } else {
            Duration totalDuration = Duration.ZERO;
            LocalDateTime earliestStart = null;
            LocalDateTime latestEnd = null;

            for (SubTask subTask : subTasks) {
                if (subTask.getStartTime() != null) {
                    if (earliestStart == null || subTask.getStartTime().isBefore(earliestStart)) {
                        earliestStart = subTask.getStartTime();
                    }
                }

                if (subTask.getEndTime() != null) {
                    if (latestEnd == null || subTask.getEndTime().isAfter(latestEnd)) {
                        latestEnd = subTask.getEndTime();
                    }
                }

                totalDuration = totalDuration.plus(subTask.getDuration());
            }

            setDuration(totalDuration);
            setStartTime(earliestStart);
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        if (getStartTime() == null || getDuration() == null) {
            return null;
        }
        return getStartTime().plus(getDuration());
    }

    public void updateStatus(Epic epic) {
        boolean isDone = true;
        boolean isNew = true;

        if (subTasks.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            for (SubTask subTask : subTasks) {
                if (subTask.getStatus() != Status.NEW) isNew = false;
                if (subTask.getStatus() != Status.DONE) isDone = false;
            }
            if (isNew) epic.setStatus(Status.NEW);
            else if (isDone) epic.setStatus(Status.DONE);
            else epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}