import model.*;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.create(new Task("Новая задача", Status.NEW, "описание"));
        Task secondTask = taskManager.create(new Task("Вторая задача", Status.NEW,"Описание 2"));
        Epic epic = taskManager.createEpic(new Epic("Новый эпик", Status.DONE, "Второе описание"));
        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание",Status.NEW, "Описание", epic));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Описание 2",Status.DONE, "Описание 2", epic));
        System.out.println("Create task: " + task);
        System.out.println("create task: " + secondTask);
        System.out.println("Create epic: " + epic);
        System.out.println("Create subTask: " + firstSubTask);
        System.out.println("Create subTask: " + secondSubTask);

        taskManager.updateEpic(epic);
        taskManager.update(task);
        System.out.println("Update epic: " + epic);

        taskManager.delete(secondTask.getId());

        taskManager.getAll();
        taskManager.getAllEpic();
        taskManager.getAllSubTask();

    }
}
