import model.*;
import service.Managers;
import service.TaskManager;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        LocalDateTime now = LocalDateTime.now();

        Task task = taskManager.createTask(new Task("Первая задача", Status.NEW, "описание", Duration.ofMinutes(50), now));
        Task task1 = taskManager.createTask(new Task("Вторая задача", Status.NEW, "описание", Duration.ofMinutes(50), now.plusHours(1)));
        Epic epic = taskManager.createEpic(new Epic("Первый эпик", Status.DONE, "Первое описание", Duration.ofMinutes(50), now.plusHours(2)));

        SubTask firstSubTask = taskManager.createSubTask(new SubTask("Описание", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(50), now.plusHours(3)));
        SubTask secondSubTask = taskManager.createSubTask(new SubTask("Название", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(50), now.plusHours(4)));
        SubTask thirdSubTask = taskManager.createSubTask(new SubTask("Слово", Status.NEW, "Описание", epic, TaskType.SUBTASK, Duration.ofMinutes(50), now.plusHours(5)));

        Epic epic1 = taskManager.createEpic(new Epic("Второй эпик", Status.DONE, "Второе описание", Duration.ofMinutes(50), now.plusHours(18)));

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
    }
}
