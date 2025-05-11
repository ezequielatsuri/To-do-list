/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import datos.Project;
import datos.Tag;
import datos.Task;
import datos.TaskList;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("boardBean")
@SessionScoped
public class BoardBean implements Serializable {

    @Inject
    private ProjectBean projectBean; // Para acceder al proyecto actual

    private String newListName;
    private String newTaskTitle;
    private String taskDescription;
    private String tagName;
    private String encargado;
    private String sourceListName;
    private String destinationListName;
    private int taskIndex;
    private int newTaskIndex;

    private Task currentTask;
    private String newTag;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public String getNewTag() {
        return newTag;
    }

    public void setNewTag(String newTag) {
        this.newTag = newTag;
    }

    public Project getCurrentProject() {
        return projectBean.getCurrentProject();
    }

    public void addList() {
        if (newListName != null && !newListName.trim().isEmpty() && getCurrentProject() != null) {
            getCurrentProject().addList(new TaskList(newListName, getCurrentProject()));
            newListName = null;
        }
    }

    public void removeList(String listName) {
        if (listName != null && getCurrentProject() != null) {
            getCurrentProject().removeList(listName);
        }
    }

    public void addTask(String listName) {
        if (listName != null && newTaskTitle != null && !newTaskTitle.trim().isEmpty() && getCurrentProject() != null) {
            TaskList list = getCurrentProject().getListByName(listName);
            if (list != null) {
                list.addTask(new Task(newTaskTitle, "")); // Inicialmente sin descripción
                newTaskTitle = null;
            }
        }
    }

    public void removeTask(String listName, int taskIndex) {
        if (listName != null && getCurrentProject() != null) {
            TaskList list = getCurrentProject().getListByName(listName);
            if (list != null && taskIndex >= 0 && taskIndex < list.getTasks().size()) {
                list.removeTask(taskIndex);
            }
        }
    }

    // Modificado para usar currentTask
    public void editTaskDetails(String listName, int taskIndex) {
        if (currentTask != null) {
            // La descripción y el encargado se actualan directamente en currentTask
            if (tagName != null && !tagName.trim().isEmpty()) {
                currentTask.addTag(new Tag(tagName.trim()));
                this.tagName = null; // Limpiar después de añadir tag
            }
            // Los cambios en currentTask se guardarán al llamar a saveTaskChanges
        }
    }

    public void moveTask(String sourceListName, String destinationListName, int taskIndex) {
        if (sourceListName != null && destinationListName != null && getCurrentProject() != null) {
            getCurrentProject().moveTask(sourceListName, taskIndex, destinationListName);
        }
    }

    public void moveTaskAt(String fromListName, String toListName, int taskIndex, int newIndex) {
        if (fromListName != null && toListName != null && getCurrentProject() != null) {
            TaskList fromList = getCurrentProject().getListByName(fromListName);
            TaskList toList = getCurrentProject().getListByName(toListName);
            if (fromList != null && toList != null && taskIndex >= 0 && taskIndex < fromList.getTasks().size()) {
                Task taskToMove = fromList.getTasks().remove(taskIndex);
                if (newIndex >= 0 && newIndex <= toList.getTasks().size()) {
                    toList.getTasks().add(newIndex, taskToMove);
                } else {
                    toList.getTasks().add(taskToMove);
                }
            }
        }
    }

    public void updateTaskDescription(Task task) {
        if (task != null) {
            task.setDescription(this.taskDescription);
            System.out.println("Descripción de la tarea actualizada a: " + this.taskDescription);
        } else {
            System.err.println("Error: La tarea a actualizar es nula.");
        }
    }

    public void openEditModal(Task task) {
        this.currentTask = task;
        this.newTag = ""; // Limpiar el campo de nueva etiqueta
        this.taskDescription = task.getDescription(); // Inicializar la descripción en el modal
        this.encargado = task.getEncargado(); // Inicializar el encargado en el modal
    }

    public void addTagToCurrentTask() {
        if (currentTask != null && newTag != null && !newTag.trim().isEmpty()) {
            currentTask.addTag(new Tag(newTag.trim()));
            this.newTag = "";
        }
    }

    public void saveTaskChanges(String listName, int taskIndex) {
    if (currentTask != null && listName != null) {
        TaskList list = getCurrentProject().getListByName(listName);
        if (list != null && taskIndex >= 0 && taskIndex < list.getTasks().size()) {
            Task taskToUpdate = list.getTask(taskIndex);

            // --- AÑADIR TAG PENDIENTE ---
            if (newTag != null && !newTag.trim().isEmpty()) {
                taskToUpdate.addTag(new Tag(newTag.trim()));
            }

            // --- ACTUALIZAR DATOS DE TAREA ---
            taskToUpdate.setDescription(currentTask.getDescription());
            taskToUpdate.setEncargado(currentTask.getEncargado());

            // --- LIMPIAR ESTADO DEL BEAN ---
            this.newTag = null;
            this.currentTask = null;

            System.out.println("Guardando cambios (y tags) en la tarea: " + taskToUpdate.getTitle());
        }
    }
}


    // Getters y Setters para newListName, newTaskTitle, taskDescription, tagName, encargado, etc.

    public String getNewListName() {
        return newListName;
    }

    public void setNewListName(String newListName) {
        this.newListName = newListName;
    }

    public String getNewTaskTitle() {
        return newTaskTitle;
    }

    public void setNewTaskTitle(String newTaskTitle) {
        this.newTaskTitle = newTaskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getSourceListName() {
        return sourceListName;
    }

    public void setSourceListName(String sourceListName) {
        this.sourceListName = sourceListName;
    }

    public String getDestinationListName() {
        return destinationListName;
    }

    public void setDestinationListName(String destinationListName) {
        this.destinationListName = destinationListName;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public void setTaskIndex(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    public int getNewTaskIndex() {
        return newTaskIndex;
    }

    public void setNewTaskIndex(int newTaskIndex) {
        this.newTaskIndex = newTaskIndex;
    }
}