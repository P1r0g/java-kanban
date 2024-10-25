package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;
    private Path path;

    public FileBackedTaskManager() {
        super();
        createResourcesTxt();
    }

    public FileBackedTaskManager(File file) {
        this.file = file;
        createResourcesTxt();
    }

    public FileBackedTaskManager(Path path) {
        this.path = path;
        createResourcesTxt();
    }

    public void save() {
        if (file == null) createResourcesTxt();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Task task : tasks.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
                writer.newLine();
            }
            for (SubTask subtask : subTasks.values()) {
                writer.write(subtask.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении");
        }
    }

    private void createResourcesTxt() {
        try {
            Path currentPath = Paths.get("").toAbsolutePath();
            Path filePath = currentPath.resolve("resources.txt");
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Файл resources.txt создан в директории: " + currentPath);
                this.file = filePath.toFile();
            } else {
                this.file = filePath.toFile();
                readFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании файла: " + e.getMessage());
        }
    }

    private void readFile() {
        if (file == null) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file, StandardCharsets.UTF_8))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] split = line.split(", ");

                    if (split.length < 5) continue;
                    Task.fromString(line, this);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task.fromString(line, manager);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return manager;
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
    public SubTask createSubTask(SubTask subtask) {
        super.createSubTask(subtask);
        save();
        return subtask;
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubTask(int id) {
        super.deleteSubTask(id);
        save();
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask updatedSubtask) {
        super.updateSubTask(updatedSubtask);
        save();
    }


    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

}