package service;

import model.*;

import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subTasks;
    private int seq;

    public TaskManager(){
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }
    private int generateID(){
        return ++seq;
    }
    public void getAll(){
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        }
    }
    public void deleteAll(){
        this.tasks = new HashMap<>();
    }
    public Task get(int id){
        return tasks.get(id);
    }

    public Task create(Task task){
        task.setId(generateID());
        tasks.put(task.getId(), task);
        return task;
    }
    public void update(Task task){
        tasks.put(task.getId(), task);
//        Task saved = tasks.get(task.getId());
//        saved.setName(task.getName());
//        saved.setStatus(task.getStatus());
    }

    public void delete(int id ){
        tasks.remove(id);
    }

    public void getAllEpic(){
        if (!epics.isEmpty()) {
            for (Task epic : epics.values()) {
                System.out.println(epic);
            }
        }
    }
    public void deleteAllEpic(){
        this.epics = new HashMap<>();
    }
    public Epic getEpic(int id){
        return epics.get(id);
    }

    public Epic createEpic(Epic epic){


        epic.setId(generateID());
        epics.put(epic.getId(), epic);
        return epic;
    }
    public void updateEpic(Epic epic){
        Epic saved = epics.get(epic.getId());
        if (saved == null){ return; }
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());

    }

    public void deleteEpic(int id ){
        epics.remove(id);
    }
    public void getSubTasksInEic(Epic epic){
        System.out.println(epic.getSubTasks());
    }

    public void getAllSubTask(Epic epic){
        for (SubTask subTask : epic.getSubTasks()) {
            System.out.print(subTask);
        }

    }
    public void deleteAllSubTask(){
        this.subTasks = new HashMap<>();
    }
    public SubTask getSubTask(int id){
        return subTasks.get(id);
    }

    public SubTask createSubTask(SubTask subTask){
        subTask.setId(generateID());
        subTasks.put(subTask.getId(), subTask);
        return subTask;
    }
    public void updateSubTask(SubTask subTask){
        subTasks.put(subTask.getId(), subTask);
    }

    public void deleteSubTask(int id ){
        subTasks.remove(id);
    }
}
