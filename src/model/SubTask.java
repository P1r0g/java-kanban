package model;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, Status status, String description, Epic epic) {
        super(name, status, description);
        setEpic(epic);
    }

    public SubTask(int id, String name, Status status, String description, Epic epic) {
        super(id, name, status, description);
        setEpic(epic);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status='" + status + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}
