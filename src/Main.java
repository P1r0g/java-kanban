import model.*;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Новая задача", Status.NEW, "описание"));
        Epic epic = taskManager.createEpic(new Epic("Новый эпик", Status.DONE, "Второе описание"));
        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание",Status.NEW, "Описание", epic));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Название", Status.NEW, "Описание", epic));
        Task taskFromManager = taskManager.getTask(task.getId());
        Epic epicFromManager = taskManager.getEpic(epic.getId());
        SubTask subTaskToUpdate = taskManager.getSubTask(firstSubTask.getId());
        subTaskToUpdate.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate);
        taskManager.updateEpic(epicFromManager);
        taskManager.delete(taskFromManager.getId());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
    }
}
