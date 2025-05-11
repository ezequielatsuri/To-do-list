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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
    // Creamos el primer proyecto con id = 1
    Project defaultProject1 = new Project("Mi Primer Proyecto");
    defaultProject1.setId(nextProjectId);
    defaultProject1.addList(new TaskList("Por hacer", defaultProject1));
    defaultProject1.addList(new TaskList("En Progreso", defaultProject1));
    defaultProject1.addList(new TaskList("Hecho", defaultProject1));
    projects.put(nextProjectId++, defaultProject1);

    // Creamos el segundo proyecto con id = 2
    Project defaultProject2 = new Project("Segundo Proyecto");
    defaultProject2.setId(nextProjectId);
    defaultProject2.addList(new TaskList("Tareas Pendientes", defaultProject2));
    defaultProject2.addList(new TaskList("Trabajando en ello", defaultProject2));
    projects.put(nextProjectId++, defaultProject2);

    // Elegimos como proyecto actual el primero
    currentProjectId = projects.keySet().iterator().next();
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
        // Le asignamos el id que vamos a usar
        newProject.setId(nextProjectId);
        // Creamos las listas iniciales
        newProject.addList(new TaskList("Por Hacer", newProject));
        newProject.addList(new TaskList("En Progreso", newProject));
        newProject.addList(new TaskList("Hecho", newProject));
        // Lo guardamos en el mapa con su id
        projects.put(nextProjectId, newProject);
        // Actualizamos el proyecto seleccionado
        currentProjectId = nextProjectId;
        nextProjectId++;
        newProjectName = null;
    }
    // Hacemos redirect pasándole el id correcto
    return "/board?faces-redirect=true&amp;projectId=" + currentProjectId;
}


    public String selectProject(Integer projectId) {
        this.currentProjectId = projectId;
        return "/board?faces-redirect=true&amp;projectId=" + projectId;
    }

    public void loadProject() {
        String projectIdStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("projectId");
        if (projectIdStr != null && !projectIdStr.isEmpty()) {
            try {
                this.currentProjectId = Integer.parseInt(projectIdStr);
            } catch (NumberFormatException e) {
                // Manejar el caso en que el projectId no es un número válido
                System.err.println("Error al convertir projectId: " + projectIdStr);
                this.currentProjectId = null;
            }
        }
        // Si currentProjectId sigue siendo null después de intentar leer el parámetro,
        // podrías establecer un proyecto por defecto o realizar otra acción.
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