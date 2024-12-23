package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, Status status, String description, Epic epic, TaskType type, Duration duration, LocalDateTime startTime) {
        super(name, status, description, duration, startTime);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(String name, String description, long duration, String startTime, Epic epic) {
        super(name, description, duration, startTime);
        setEpic(epic);
    }

    public SubTask(String name, Status status, String description, Epic epic, Duration duration, LocalDateTime startTime) {
        super(name, status, description, duration, startTime);
        setEpic(epic);
        this.type = TaskType.SUBTASK;
    }

    public SubTask(int id, String name, Status status, String description, Epic epic, TaskType type, Duration duration, LocalDateTime startTime) {
        super(id, name, status, description, duration, startTime);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime, Epic epic) {
        super(id, name, status, description, duration, startTime);
        setEpic(epic);
        this.type = TaskType.SUBTASK;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        if (epic != null) {
            this.epic = epic;
            this.epic.addTask(this);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "," + epic;
    }

    public TaskType getType() {
        return this.type;
    }

    @Override
    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            return startTime.plus(duration);
        }
        return null;
    }
}
