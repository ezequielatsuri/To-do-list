/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;


import datos.Project;
import datos.TaskList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

@Named("projectBean")
@SessionScoped
public class ProjectBean implements Serializable {

    private Map<Integer, Project> projects;
    private Integer currentProjectId;
    private String newProjectName;
    private static int nextProjectId = 1;

    @Inject
    private LoginBean loginBean; // Para acceder al usuario logueado (si es necesario)

    public ProjectBean() {
        projects = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        // Inicializamos con un proyecto por defecto si no hay ninguno
        if (projects.isEmpty()) {
            Project defaultProject = new Project("Mi Primer Proyecto");
            defaultProject.addList(new TaskList("Por Hacer", defaultProject));
            defaultProject.addList(new TaskList("En Progreso", defaultProject));
            defaultProject.addList(new TaskList("Hecho", defaultProject));
            projects.put(nextProjectId++, defaultProject);
            currentProjectId = 1; // Establecer el ID del primer proyecto como actual
        }
    }

    public List<Project> getProjectList() {
        return new ArrayList<>(projects.values());
    }

    public Project getCurrentProject() {
        if (currentProjectId != null && projects.containsKey(currentProjectId)) {
            return projects.get(currentProjectId);
        }
        return null;
    }

    public String addProject() {
        if (newProjectName != null && !newProjectName.trim().isEmpty()) {
            Project newProject = new Project(newProjectName);
            newProject.addList(new TaskList("Por Hacer", newProject));
            newProject.addList(new TaskList("En Progreso", newProject));
            newProject.addList(new TaskList("Hecho", newProject));
            projects.put(nextProjectId, newProject);
            currentProjectId = nextProjectId++;
            newProjectName = null; // Limpiar el campo después de agregar
        }
        return "/board?faces-redirect=true";
    }

    public String selectProject(Integer projectId) {
        this.currentProjectId = projectId;
        return "/board?faces-redirect=true";
    }

    public String getNewProjectName() {
        return newProjectName;
    }

    public void setNewProjectName(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    public Map<Integer, Project> getProjects() {
        return projects;
    }

    public void setProjects(Map<Integer, Project> projects) {
        this.projects = projects;
    }

    public Integer getCurrentProjectId() {
        return currentProjectId;
    }

    public void setCurrentProjectId(Integer currentProjectId) {
        this.currentProjectId = currentProjectId;
    }
}