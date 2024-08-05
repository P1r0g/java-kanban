package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final String file = "C:\\Users\\pirog\\IdeaProjects\\java-kanban\\src\\service\\file.cvs";

    public FileBackedTaskManager() {
        super();
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id,type,name,status,description");
            writer.newLine();
            for (Task task : tasks.values()) {
                writer.write(toString(task));
                writer.newLine();
            }
            for (Epic epic : epics.values()) {
                writer.write(toString(epic));
                writer.newLine();
            }
            for (SubTask subTask : subTasks.values()) {
                writer.write(toString(subTask));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask;
    }

    private String toString(Task task){
        return task.getId() + "," +
                task.getType().toString() + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription();
    }
}
