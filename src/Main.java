import model.*;
import service.Managers;
import service.TaskManager;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task = taskManager.createTask(new Task("Первая задача", Status.NEW, "описание", Duration.ofMinutes(60), LocalDateTime.now()));
        Task task1 = taskManager.createTask(new Task("Вторая задача", Status.NEW, "описание", Duration.ofMinutes(90), LocalDateTime.now().plusHours(1)));
        Epic epic = taskManager.createEpic(new Epic("Первый эпик", Status.DONE, "Второе описание", Duration.ofMinutes(180), LocalDateTime.now()));

        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(30)));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Название", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(60), LocalDateTime.now().plusHours(1)));
        SubTask thirdSubTask = taskManager.createSubTask(new SubTask("Слово", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(75), LocalDateTime.now().plusHours(2)));

        Epic epic1 = taskManager.createEpic(new Epic("Второй эпик", Status.DONE, "Второе описание", Duration.ofMinutes(240), LocalDateTime.now().plusHours(3)));

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager.getHistoryManager());

        // Обновляем задачи с изменением статуса
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
        subTaskToUpdate1.setStatus(Status.NEW);
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

        // Добавляем задачи с новыми полями
        Task task2 = taskManager.createTask(new Task("name", Status.NEW, "fnsdjifs", Duration.ofMinutes(30), LocalDateTime.now()));
        Epic epicc = taskManager.createEpic(new Epic("dfdf", Status.NEW, "adsfsdf", Duration.ofMinutes(120), LocalDateTime.now().plusHours(1)));
        SubTask subTask = taskManager.createSubTask(new SubTask("sfgsdf", Status.IN_PROGRESS, "fgdfgdf", epicc, TaskType.SUBTASK, Duration.ofMinutes(50), LocalDateTime.now().plusMinutes(90)));
        Task tasks = taskManager.createTask(new Task("sdf", Status.NEW, "afsaf", Duration.ofMinutes(40), LocalDateTime.now()));
        Task taskss = taskManager.createTask(new Task("sdf", Status.NEW, "afsaf", Duration.ofMinutes(60), LocalDateTime.now()));

        taskManager.updateTask(taskss);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllSubTask());
    }
}
