package service;

import java.io.File;

public class Managers {
    static String path = "resources.txt";


    public static TaskManager getDefault() {
        return FileBackedTaskManager.loadFromFile(new File(path));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
