package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks = new ArrayList<>();


    public Epic(String name, Status status, String description) {
        super(name, status, description);
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void addTask(SubTask subTask) {
        if (subTask.getId() != this.getId()) subTasks.add(subTask);
    }


    public void updateStatus(Epic epic) {
        boolean isDone = true;
        boolean isNew = true;

        if (subTasks.isEmpty()) epic.setStatus(Status.NEW);
        else {
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
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status='" + status + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}