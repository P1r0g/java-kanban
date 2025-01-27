package http;

import com.sun.net.httpserver.HttpExchange;
import service.InMemoryTaskManager;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseHttpHandler {
    protected InMemoryTaskManager manager;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected BaseHttpHandler(InMemoryTaskManager manager) {
        this.manager = manager;
    }


    public Optional<Integer> getTaskId(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public void writeResponse(HttpExchange exchange,
                              String responseString,
                              int responseCode) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(responseCode, 0);
            os.write(responseString.getBytes());
        }
        exchange.close();
    }

    public HttpMethod getEndpoint(String requestPath, String requestMethod, String userPath) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 2 && pathParts[1].equals(userPath) && requestMethod.equals("GET")) {
            return HttpMethod.GET;
        } else if (pathParts.length == 2 && pathParts[1].equals(userPath) && requestMethod.equals("POST")) {
            return HttpMethod.POST;
        } else if (pathParts.length == 3 && pathParts[1].equals(userPath) && requestMethod.equals("DELETE")) {
            return HttpMethod.DELETE;
        }
        return HttpMethod.UNKNOWN;
    }

    protected void handleGet(HttpExchange exchange) throws IOException {
        String response = manager.getAllTasks().stream().map(Task::toString).collect(Collectors.joining("\n"));
        writeResponse(exchange, response, 200);
    }

    protected void handlePost(HttpExchange exchange) throws IOException {
        Optional<Task> taskBody = parseTask(exchange.getRequestBody(), manager);
        Task taskNew = taskBody.get();
        exchange.sendResponseHeaders(201, 0);
        writeResponse(exchange, "Задача добавлена", 201);
        for (Task task : manager.getAllTasks()) {
            if (taskNew.getId() == task.getId()) {
                writeResponse(exchange, "Задача с идентификатором " + task.getId() + "уже существует", 404);
            }
        }
        manager.getAllTasks().add(taskNew);
        writeResponse(exchange, "Задача добавлена", 201);
    }

    protected void handleDeleteTaskById(HttpExchange exchange, InMemoryTaskManager manager) throws IOException {
        Optional<Integer> postIdOpt = getTaskId(exchange);
        if (postIdOpt.isEmpty()) {
            writeResponse(exchange, "Некорректный id", 400);
            return;
        }
        int taskId = postIdOpt.get();
        manager.delete(taskId);
        exchange.sendResponseHeaders(200, 0);
        writeResponse(exchange, "Задача успешно удалена", 200);
    }

    public Optional<Task> parseTask(InputStream bodyInputStream, InMemoryTaskManager manager) throws IOException {
        String body = new String(bodyInputStream.readAllBytes(), DEFAULT_CHARSET);
        String name;
        String description;
        long duration;
        String startTime;
        if (body.isEmpty()) {
            return Optional.empty();
        } else {
            String[] newLineInd = body.split(",");
            name = newLineInd[2];
            description = newLineInd[4];
            duration = Long.parseLong(newLineInd[6]);
            startTime = newLineInd[7];
            Task task = new Task(name, description, duration, startTime);
            return Optional.of(manager.createTask(task));
        }
    }
}