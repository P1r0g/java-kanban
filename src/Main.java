import model.*;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task = taskManager.createTask(new Task("Первая задача", Status.NEW, "описание"));
        Task task1 = taskManager.createTask(new Task("Вторая задача", Status.NEW, "описание"));
        Epic epic = taskManager.createEpic(new Epic("Первый эпик", Status.DONE, "Второе описание"));
        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание",Status.NEW, "Описание", epic));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Название", Status.NEW, "Описание", epic));
        SubTask thirdSubTask = taskManager.createSubTask(new SubTask("Слово",Status.NEW, "Описание", epic));
        Epic epic1 = taskManager.createEpic(new Epic("Второй эпик", Status.DONE, "Второе описание"));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager.getHistoryManager());

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
        System.out.println(taskManager.getHistoryManager());
        taskManager.deleteSubTask(firstSubTask.getId());

        System.out.println("-----");

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager.getHistoryManager());
    }
}
