package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import http.BaseHttpHandler;
import http.HttpMethod;
import service.InMemoryTaskManager;
import model.Task;

import java.io.IOException;
import java.util.stream.Collectors;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {

    public HistoryHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpMethod endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), "history");
        switch (endpoint) {
            case GET: {
                handleGet(exchange);
                break;
            }
            default:
                writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    @Override
    protected void handleGet(HttpExchange exchange) throws IOException {
        String response = manager.getHistoryManager().stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));
        writeResponse(exchange, response, 200);
    }
}