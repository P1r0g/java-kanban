package service;

import java.io.File;

public class Managers {
    static String path = "C:/Users/pirog/IdeaProjects/java-kanban/resources.txt";


    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile(new File(path));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
