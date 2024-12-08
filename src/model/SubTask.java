package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(int id, String name, Status status, String description, Epic epic) {
        super(id, name, status, description);
        setEpic(epic);
        this.type = TaskType.SUBTASK;
    }

    public SubTask(int id, String name, Status status, String description, Epic epic, TaskType type) {
        super(id, name, status, description);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(String name, Status status, String description, Epic epic) {
        super(name, status, description);
        setEpic(epic);
        this.type = TaskType.SUBTASK;
    }

    public SubTask(String name, Status status, String description, Epic epic, TaskType type) {
        super(name, status, description);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(String name, Status status, String description, Epic epic, TaskType type, Duration duration, LocalDateTime startTime) {
        super(name, status, description, duration, startTime);
        setEpic(epic);
        this.type = type;
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

    public SubTask(int id, String name, Status status, String description, Epic epic, Duration duration, LocalDateTime startTime) {
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
        return super.toString() + ", " + epic;
    }

    public TaskType getType() {
        return this.type;
    }
}
