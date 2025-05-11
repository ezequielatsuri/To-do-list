package control;

import datos.Project;
import datos.Tag;
import datos.Task;
import datos.TaskList;
import datos.User;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
    private static final String PROJECTS_SESSION_KEY = "projects";
    private static final String CURRENT_PROJECT_ID_SESSION_KEY = "currentProjectId";
    private static final String USER_SESSION_KEY = "loggedInUser";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute(USER_SESSION_KEY);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Map<Integer, Project> projects = (Map<Integer, Project>) session.getAttribute(PROJECTS_SESSION_KEY);
        Integer currentProjectId = (Integer) session.getAttribute(CURRENT_PROJECT_ID_SESSION_KEY);
        Project currentProject = null;
        if (projects != null && currentProjectId != null) {
            currentProject = projects.get(currentProjectId);
        }

        if (currentProject == null) {
            response.sendRedirect(request.getContextPath() + "/projects");
            return;
        }

        String action = request.getParameter("action");

        if ("addList".equals(action)) {
            String newListName = request.getParameter("newListName");
            if (newListName != null && !newListName.trim().isEmpty()) {
                currentProject.addList(new TaskList(newListName, currentProject));
            }
            projects.put(currentProjectId, currentProject);

        } else if ("removeList".equals(action)) {
            String listToRemove = request.getParameter("listToRemove");
            if (listToRemove != null) {
                currentProject.removeList(listToRemove); 
            }
            projects.put(currentProjectId, currentProject);

        } else if ("addTask".equals(action)) {
            String listName = request.getParameter("listName");
            String taskTitle = request.getParameter("taskDescription");
            String taskDescription = " ";
            if (listName != null && taskTitle != null && !taskTitle.trim().isEmpty()) {
                TaskList list = currentProject.getListByName(listName);
                if (list != null) {
                    list.addTask(new Task(taskTitle, taskDescription));
                }
            }
            projects.put(currentProjectId, currentProject);

        } else if ("editTask".equals(action)) {
            String listName = request.getParameter("listName");
            String taskIndexStr = request.getParameter("taskIndex");
            String newTaskDescription = request.getParameter("newTaskDescription");
            if (listName != null && taskIndexStr != null && newTaskDescription != null && !newTaskDescription.trim().isEmpty()) {
                try {
                    int taskIndex = Integer.parseInt(taskIndexStr);
                    TaskList list = currentProject.getListByName(listName);
                    if (list != null) {
                        Task taskToEdit = list.getTask(taskIndex);
                        if (taskToEdit != null) {
                            taskToEdit.setDescription(newTaskDescription);
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle error
                }
            }
            projects.put(currentProjectId, currentProject);

        } else if ("removeTask".equals(action)) {
            String listName = request.getParameter("listName");
            String taskIndexStr = request.getParameter("taskIndex");
            if (listName != null && taskIndexStr != null) {
                try {
                    int taskIndex = Integer.parseInt(taskIndexStr);
                    TaskList list = currentProject.getListByName(listName);
                    if (list != null) {
                        list.removeTask(taskIndex);
                    }
                } catch (NumberFormatException e) {
                    // Handle error
                }
            }
            projects.put(currentProjectId, currentProject);

        } else if ("moveTask".equals(action)) {
            String sourceList = request.getParameter("sourceList");
            String taskIndexStr = request.getParameter("taskIndex");
            String destinationList = request.getParameter("destinationList");
            if (sourceList != null && taskIndexStr != null && destinationList != null) {
                try {
                    int taskIndex = Integer.parseInt(taskIndexStr);
                    TaskList sourceTaskList = currentProject.getListByName(sourceList);
                    TaskList destinationTaskList = currentProject.getListByName(destinationList);
                    if (sourceTaskList != null && destinationTaskList != null) {
                        Task taskToMove = sourceTaskList.getTask(taskIndex);
                        if (taskToMove != null) {
                            sourceTaskList.removeTask(taskIndex);
                            destinationTaskList.addTask(taskToMove);
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle error
                }
            }
            projects.put(currentProjectId, currentProject);

        } else if ("addTagToTask".equals(action)) {
            String listName = request.getParameter("listName");
            String taskIndexStr = request.getParameter("taskIndex");
            String tagName = request.getParameter("tagName");
            if (listName != null && taskIndexStr != null && tagName != null && !tagName.trim().isEmpty()) {
                try {
                    int taskIndex = Integer.parseInt(taskIndexStr);
                    TaskList list = currentProject.getListByName(listName);
                    if (list != null) {
                        Task task = list.getTask(taskIndex);
                        if (task != null) {
                            task.addTag(new Tag(tagName));
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle error
                }
            }
            projects.put(currentProjectId, currentProject);

        }  else if ("editTaskDetails".equals(action)) {
    String listName = request.getParameter("listName");
    String taskIndexStr = request.getParameter("taskIndex");
    String description = request.getParameter("description");
    String tagName = request.getParameter("tagName");
    String encargado = request.getParameter("encargado"); // 🆕 Captura del encargado

    if (listName != null && taskIndexStr != null) {
        try {
            int taskIndex = Integer.parseInt(taskIndexStr);
            TaskList list = currentProject.getListByName(listName);
            if (list != null) {
                Task task = list.getTask(taskIndex);
                if (task != null) {
                    if (description != null && !description.trim().isEmpty()) {
                        task.setDescription(description.trim());
                    }
                    if (tagName != null && !tagName.trim().isEmpty()) {
                        task.addTag(new Tag(tagName.trim()));
                    }
                    if (encargado != null && !encargado.trim().isEmpty()) {
                        task.setEncargado(encargado.trim()); // ✅ Guardar el encargado
                    }
                }
            }
        } catch (NumberFormatException e) {
            // Manejo de error por índice inválido
            System.err.println("Error al parsear taskIndex: " + taskIndexStr);
        }
    }
}else if ("moveTaskAt".equals(action)) {
            System.out.println("===> Entró a moveTaskAt");

            String fromListName = request.getParameter("fromList");
            String toListName = request.getParameter("toList");
            int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
            int newIndex = Integer.parseInt(request.getParameter("newIndex"));

            if (currentProject != null) {
                TaskList fromList = currentProject.getListByName(fromListName);
                TaskList toList = currentProject.getListByName(toListName);

                if (fromList != null && toList != null && taskIndex < fromList.getTasks().size()) {
                    Task task = fromList.getTasks().remove(taskIndex);
                    if (newIndex >= 0 && newIndex <= toList.getTasks().size()) {
                        toList.getTasks().add(newIndex, task);
                    } else {
                        toList.getTasks().add(task);
                    }
                }
                projects.put(currentProjectId, currentProject);
                session.setAttribute(PROJECTS_SESSION_KEY, projects);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Actualiza el mapa en la sesión solo una vez aquí
        session.setAttribute(PROJECTS_SESSION_KEY, projects);
        request.setAttribute("currentProject", currentProject);
        request.getRequestDispatcher("/boardView.jsp").forward(request, response);

        // Debugging logs
        System.out.println("BoardServlet - Current Project ID from Session: " + currentProjectId);
        if (currentProject == null) {
            System.out.println("BoardServlet - Current Project is NULL.");
        } else {
            System.out.println("BoardServlet - Current Project Name: " + currentProject.getName());
            System.out.println("BoardServlet - Number of Lists: " + currentProject.getLists().size());
            for (TaskList list : currentProject.getLists()) {
                System.out.println("  List Name: " + list.getName() + ", Number of Tasks: " + list.getTasks().size());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
