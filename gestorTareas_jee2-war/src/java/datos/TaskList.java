/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskList  implements Serializable {
    private int id; 
    private String name;
    private Project project; // Referencia al proyecto al que pertenece la lista
    private List<Task> tasks;

    // Constructor
    public TaskList(String name, Project project) {
        this.name = name;
        this.project = project;
        this.tasks = new ArrayList<>();
    }

    // Getters y Setters para id, name, project, tasks

    // Métodos para agregar, eliminar, obtener tareas
    public void addTask(Task task) {
        this.tasks.add(task);
    }
    public void addTaskAt(int indice, Task task) {
        if (indice >= 0 && indice <= tasks.size()) {
        tasks.add(indice, task);
        } else {
        tasks.add(task); 
        }
    }


    public Task removeTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.remove(index);
            Task t = getTask(index);
            return t;
        }
        return null;
    }

    public Task getTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            return this.tasks.get(index);
        }
        return null;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
