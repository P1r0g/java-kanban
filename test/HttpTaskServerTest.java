import http.HttpTaskServer;
import model.Epic;
import model.Task;
import model.SubTask;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    private static final InMemoryTaskManager manager = new InMemoryTaskManager();
    static HttpTaskServer httpTaskServer;

    @BeforeAll
    static void beforeAll() {
        Task task1 = new Task("Task1", "description task 1", 60, "12:00 26.11.2024");
        Epic epic = new Epic("Epic1", "description epic 1", 60, "12:00 27.11.2024");
        SubTask subtask = new SubTask("SubTask1", "description task 1", 60, "19:00 26.11.2024", epic);
        manager.createTask(task1);
        manager.createEpic(epic);
        manager.createSubTask(subtask);
        try {
            httpTaskServer = new HttpTaskServer(manager);
            httpTaskServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @AfterAll
    static void afterAll() {
        httpTaskServer.stop();
    }

    @Test
    public void shouldBeEquals200ByTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = stringRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void shouldBeEquals200BySubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = stringRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void shouldBeEquals200ByEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = stringRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void shouldBeEquals404ByUnknownEndPoint() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/test");
        HttpRequest request = stringRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 404);
    }



    public HttpRequest stringRequest(URI url) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .build();
    }
}