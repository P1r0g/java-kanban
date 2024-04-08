package model;

public class SubTask extends Task {
    private Epic epic;
    private int epicId;

    public SubTask(String name, Status status, String description) {
        super(name, status, description);
    }

}
