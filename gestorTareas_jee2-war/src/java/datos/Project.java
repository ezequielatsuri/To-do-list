/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author ezequ
 */
// src/main/java/com/example/model/Project.java


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Project  implements Serializable {
    private int id;
    private String name;
    private List<TaskList> lists;

    public Project(String name) {
        this.name = name;
        this.lists = new ArrayList<>();
       
    }

    public void addList(TaskList listName) {
       // TaskList newList = new TaskList(listName, this); // Pasar la instancia del proyecto
        this.lists.add(listName);
    }

    public void removeList(String listName) {
        this.lists.removeIf(list -> list.getName().equals(listName));
    }

    public TaskList getListByName(String listName) {
        for (TaskList list : this.lists) {
            if (list.getName().equals(listName)) {
                return list;
            }
        }
        return null;
    }

    public void addTaskToList(String listName, Task task) {
        TaskList list = getListByName(listName);
        if (list != null) {
            list.addTask(task);
        }
    }

    public void removeTaskFromList(String listName, int taskIndex) {
        TaskList list = getListByName(listName);
        if (list != null) {
            list.removeTask(taskIndex);
        }
    }

    public Task getTaskFromList(String listName, int taskIndex) {
        TaskList list = getListByName(listName);
        if (list != null) {
            return list.getTask(taskIndex);
        }
        return null;
    }

    public void moveTask(String sourceListName, int taskIndex, String destinationListName) {
        TaskList sourceList = getListByName(sourceListName);
        TaskList destinationList = getListByName(destinationListName);
        if (sourceList != null && destinationList != null) {
            Task taskToMove = sourceList.getTask(taskIndex);
            if (taskToMove != null) {
                sourceList.removeTask(taskIndex);
                destinationList.addTask(taskToMove);
            }
        }
    }

    // Getters y Setters para id, name, lists
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskList> getLists() {
        return lists;
    }

    public void setLists(List<TaskList> lists) {
        this.lists = lists;
    }
}