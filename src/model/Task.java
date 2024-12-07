package model;

import service.FileBackedTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private final TaskType type;
    private int id;
    private String name;
    protected Status status;
    private String description;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(int id, String name, Status status, String description) {
        setId(id);
        setName(name);
        setStatus(status);
        setDescription(description);
        this.type = TaskType.TASK;
    }

    public Task(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        setId(id);
        setName(name);
        setStatus(status);
        setDescription(description);
        setDuration(duration);
        setStartTime(startTime);
        this.type = TaskType.TASK;
    }

    public Task(String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        setName(name);
        setStatus(status);
        setDescription(description);
        setDuration(duration);
        setStartTime(startTime);
        this.type = TaskType.TASK;
    }

    public Task(String name, Status status, String description) {
        setName(name);
        setStatus(status);
        setDescription(description);
        this.type = TaskType.TASK;
    }

    public Task(String name, Status status, String description, TaskType type) {
        this.type = type;
        setName(name);
        setStatus(status);
        setDescription(description);
    }

    public static void fromString(String str, FileBackedTaskManager manager) {
        String[] line = str.split(", ");
        if (line.length < 5) {
            throw new IllegalArgumentException("Недостаточно параметров для создания задачи");
        }
        Status status = Status.valueOf(line[3]);
        String name = line[2];
        String description = line[4];
        int id = Integer.parseInt(line[0]);
        switch (line[1]) {
            case "TASK":
                manager.createTask(new Task(id, name, status, description));
                break;
            default:
                break;
            case "EPIC":
                manager.createEpic(new Epic(id, name, status, description));
                break;
            case "SUBTASK":
                int epicId = Integer.parseInt(line[5]);
                manager.createSubTask(new SubTask(id, name, status, description, manager.getEpic(epicId)));
                break;
        }
    }
    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Epic getEpic() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && type == task.type && Objects.equals(name, task.name) && status == task.status && Objects.equals(description, task.description) && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, name, status, description, duration, startTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }
}
