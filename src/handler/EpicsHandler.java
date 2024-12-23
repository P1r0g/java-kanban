package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.BaseHttpHandler;
import http.HttpMethod;
import model.Task;
import service.InMemoryTaskManager;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    ;

    public EpicsHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpMethod endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), "epics");
        switch (endpoint) {
            case GET: {
                handleGet(exchange);
                break;
            }
            case POST: {
                handlePost(exchange);
                break;
            }
            case DELETE: {
                handleDeleteTaskById(exchange, manager);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String response = manager.getAllEpic().stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));
        writeResponse(exchange, response, 200);
    }

    @Override
    protected void handlePost(HttpExchange exchange) throws IOException {
        Optional<Task> taskBody = parseTask(exchange.getRequestBody(), manager);
        Task taskNew = taskBody.get();
        exchange.sendResponseHeaders(201, 0);
        writeResponse(exchange, "Задача добавлена", 201);
        for (Task task : manager.getAllEpic()) {
            if (taskNew.getId() == task.getId()) {
                writeResponse(exchange, "Задача с идентификатором " + task.getId() + "уже существует", 404);
            }
        }
        manager.getAllTasks().add(taskNew);
        writeResponse(exchange, "Задача добавлена", 201);
    }
}