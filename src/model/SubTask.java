package model;

public class SubTask extends Task {
    private Epic epic;
    private final TaskType type;

    public SubTask(String name, Status status, String description, Epic epic, TaskType type) {
        super(name, status, description);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(String name, Status status, String description, Epic epic) {
        super(name, status, description);
        setEpic(epic);
        this.type = TaskType.SUBTASK;
    }

    public SubTask(int id, String name, Status status, String description, Epic epic, TaskType type) {
        super(id, name, status, description);
        setEpic(epic);
        this.type = type;
    }

    public SubTask(int id, String name, Status status, String description, Epic epic) {
        super(id, name, status, description);
        this.epic = epic;
        this.type = TaskType.SUBTASK;
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }


    public TaskType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return super.toString() + "," + epic;
    }
}
