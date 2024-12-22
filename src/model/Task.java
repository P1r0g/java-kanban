package model;

import service.FileBackedTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected TaskType type;
    protected int id;
    protected String name;
    protected Status status;
    protected String description;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public Task(int id, String name, Status status, String description, Duration duration, LocalDateTime startTime) {
        setId(id);
        setName(name);
        setStatus(status);
        setDescription(description);
        setDuration(duration);
        setStartTime(startTime);
        this.type = TaskType.TASK;
    }

    public Task(String name, String description, long duration, String startTime) {
        setName(name);
        setDescription(description);
        setDuration(Duration.ofMinutes(duration));
        setStartTime(LocalDateTime.parse(startTime));
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

    public static void fromString(String str, FileBackedTaskManager manager) {
        if (str.equals("id,type,name,status,description,epic,duration,startTime")) return;
        String[] line = str.split(",");
        if (line.length < 5) {
            throw new IllegalArgumentException("Недостаточно параметров для создания задачи");
        }

        int id = Integer.parseInt(line[0]);
        TaskType type = TaskType.valueOf(line[1]);
        String name = line[2];
        Status status = Status.valueOf(line[3]);
        String description = line[4];

        Duration duration = Duration.ofMinutes(Long.parseLong(line[5]));
        LocalDateTime startTime = LocalDateTime.parse(line[6]);

        switch (type) {
            case TASK:
                manager.createTask(new Task(id, name, status, description, duration, startTime));
                break;
            case EPIC:
                manager.createEpic(new Epic(id, name, status, description, duration, startTime));
                break;
            case SUBTASK:
                int epicId = Integer.parseInt(line[7]);
                manager.createSubTask(new SubTask(id, name, status, description, duration, startTime, manager.getEpic(epicId)));
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }


    public LocalDateTime getEndTime() {
        if (startTime == null || duration == null) {
            return null;
        }
        return startTime.plusMinutes(duration.toMinutes());
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
        return id + "," + type + "," + name + "," + status + "," + description + "," +
                (duration != null ? duration.toMinutes() : "") + "," +
                (startTime != null ? startTime.toString() : "");
    }

}
