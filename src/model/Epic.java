package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final List<SubTask> subTasks = new ArrayList<>();

    public Epic(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        super(id, name, status, description, duration, startTime);
        this.type = TaskType.EPIC;
    }

    public Epic(String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        super(name, status, description, duration, startTime);
        this.type = TaskType.EPIC;
    }

    public Epic(String name, String description, long duration, String startTime) {
        super(name, description, duration, startTime);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addTask(SubTask subTask) {
        if (this.getId() == subTask.getId()) {
            System.out.println("Нельзя добавить epic в subTaskList");
        } else {
            subTasks.add(subTask);
            calculateTime();
        }
    }

    public void removeTask(SubTask subTask) {
        subTasks.remove(subTask);
        calculateTime();
    }


    public void updateStatus() {
        boolean isDone = true;
        boolean isNew = true;

        if (subTasks.isEmpty()) {
            setStatus(Status.NEW);
        } else {
            for (SubTask subTask : subTasks) {
                if (subTask.getStatus() != Status.NEW) isNew = false;
                if (subTask.getStatus() != Status.DONE) isDone = false;
            }
            if (isNew) setStatus(Status.NEW);
            else if (isDone) setStatus(Status.DONE);
            else setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private void calculateStartTime() {
        LocalDateTime startSubtask = LocalDateTime.now();
        if (!subTasks.isEmpty()) {
            for (SubTask subtask : subTasks) {
                if (subtask.getStartTime() != null) {
                    if (subtask.getStartTime().isBefore(startSubtask)) {
                        startSubtask = subtask.getStartTime();
                    }
                }
            }
        }
        setStartTime(startSubtask);
    }

    private void calculateEndTime() {
        if (subTasks == null || subTasks.isEmpty()) {
            this.endTime = null;
            return;
        }

        this.endTime = subTasks.stream()
                .map(SubTask::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);
    }


    private void calculateDuration() {
        setDuration(Duration.between(this.startTime, this.endTime));
    }

    public void calculateTime() {
        calculateStartTime();
        calculateEndTime();
        calculateDuration();
    }
}
