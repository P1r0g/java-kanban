import model.*;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.create(new Task("Новая задача", Status.NEW, "описание"));
        Epic epic = taskManager.createEpic(new Epic("Новый эпик", Status.DONE, "Второе описание"));
        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание",Status.DONE, "Описание", epic));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Описание 2",Status.DONE, "Описание 2", epic));
        System.out.println("Create task: " + task);
        System.out.println("Create epic: " + epic);
        System.out.println("Create subTask: " + firstSubTask);
        System.out.println("Create subTask: " + secondSubTask);

        Task taskFromManager = taskManager.get(task.getId());
        Epic epicFromManager = taskManager.getEpic(epic.getId());
        SubTask firstSubTaskFromManager = taskManager.getSubTask(firstSubTask.getId());
        SubTask secondSubTaskFromManager = taskManager.getSubTask(secondSubTask.getId());
        System.out.println("Get task: " + taskFromManager);
        System.out.println("Get epic: " + epicFromManager);
        System.out.println("Get first sub task: " + firstSubTaskFromManager);
        System.out.println("Get second sub task: " + secondSubTaskFromManager);


        taskFromManager.setName("New name");
        taskManager.update(taskFromManager);
        System.out.println("Update task: " + taskFromManager);

        taskManager.delete(taskFromManager.getId());
        System.out.println("Delete: " + task);

        taskManager.getAll();
    }
}
