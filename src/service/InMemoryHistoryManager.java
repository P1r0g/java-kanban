package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node {
        Task item;
        Node next, prev;

        Node(Node next, Task element, Node prev) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }

    private HashMap<Integer, Node> history = new HashMap<>();
    Node first;
    Node last;

    @Override
    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        Node current = first;
        while (current != null) {
            tasks.add(current.item);
            current = current.next;
        }
        return tasks;
    }

    @Override
    public void addInHistory(Task task) {
        Node node = history.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node != null) removeNode(node);
    }

    private void linkLast(Task task) {
        final Node l = last;
        final Node newNode = new Node(null, task, l);
        last = newNode;
        if (l == null) first = newNode;
        else l.next = newNode;
        history.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        history.remove(node.item.getId());
    }
}
