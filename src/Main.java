import model.*;
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
        Task taskFromManager1 = taskManager.getTask(task1.getId());
        taskFromManager1.setStatus(Status.DONE);
        taskManager.updateTask(taskFromManager1);
        SubTask subTaskToUpdate = taskManager.getSubTask(firstSubTask.getId());
        subTaskToUpdate.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate);
        SubTask subTaskToUpdate1 = taskManager.getSubTask(secondSubTask.getId());
        subTaskToUpdate1.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate1);
        SubTask subTaskToUpdate2 = taskManager.getSubTask(thirdSubTask.getId());
        subTaskToUpdate2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTaskToUpdate2);
        Epic epicToUpdate = taskManager.getEpic(epic1.getId());
        epicToUpdate.setStatus(Status.DONE);
        taskManager.updateEpic(epicToUpdate);



        System.out.println("-----");

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager.getHistoryManager());
        taskManager.deleteAllSubTask();
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpic();

        System.out.println("-----");

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager.getHistoryManager());

        System.out.println("-------");

        Task task2 = taskManager.createTask(new Task("name", Status.NEW, "fnsdjifs"));
    }
}
