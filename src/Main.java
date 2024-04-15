import model.*;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Новая задача", Status.NEW, "описание"));
        Epic epic = taskManager.createEpic(new Epic("Новый эпик", Status.DONE, "Второе описание"));
        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание",Status.NEW, "Описание", epic));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Название", Status.NEW, "Описание", epic));
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        Task taskFromManager = taskManager.getTask(task.getId());
        taskFromManager.setStatus(Status.DONE);
        taskManager.updateTask(taskFromManager);
        SubTask subTaskToUpdate = taskManager.getSubTask(firstSubTask.getId());
        subTaskToUpdate.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate);
        SubTask subTaskToUpdate1 = taskManager.getSubTask(secondSubTask.getId());
        subTaskToUpdate1.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate1);
        System.out.println("-----");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        taskManager.deleteAllSubTask();
        System.out.println("-----");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
    }
}
