package control;

import datos.Project;
import datos.TaskList;
import datos.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private static final String PROJECTS_SESSION_KEY = "projects";
    private static final String CURRENT_PROJECT_ID_SESSION_KEY = "currentProjectId";
    private static final String USER_SESSION_KEY = "loggedInUser";
    private static int nextProjectId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute(USER_SESSION_KEY);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        Map<Integer, Project> projects = (Map<Integer, Project>) session.getAttribute(PROJECTS_SESSION_KEY);

        if (projects == null) {
            projects = new HashMap<>();
            session.setAttribute(PROJECTS_SESSION_KEY, projects);
        }

        if (projects.isEmpty()) {
            Project defaultProject = new Project("Mi Primer Proyecto");
            defaultProject.addList(new TaskList("Por Hacer", defaultProject));
            defaultProject.addList(new TaskList("En Progreso", defaultProject));
            defaultProject.addList(new TaskList("Hecho", defaultProject));
            projects.put(nextProjectId, defaultProject);
            session.setAttribute(CURRENT_PROJECT_ID_SESSION_KEY, nextProjectId);
            nextProjectId++;
            response.sendRedirect(request.getContextPath() + "/board");
            return;
        }

        if ("selectProject".equals(action)) {
            String projectIdStr = request.getParameter("projectId");
            if (projectIdStr != null) {
                try {
                    int projectId = Integer.parseInt(projectIdStr);
                    session.setAttribute(CURRENT_PROJECT_ID_SESSION_KEY, projectId);
                    response.sendRedirect(request.getContextPath() + "/board");
                    return;
                } catch (NumberFormatException e) {
                    
                }
            }
        }

        if (session.getAttribute(CURRENT_PROJECT_ID_SESSION_KEY) != null && !"selectProject".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/board");
            return;
        }

        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/boardView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute(USER_SESSION_KEY);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Map<Integer, Project> projects = (Map<Integer, Project>) session.getAttribute(PROJECTS_SESSION_KEY);
        if (projects == null) {
            projects = new HashMap<>();
            session.setAttribute(PROJECTS_SESSION_KEY, projects);
        }

        String action = request.getParameter("action");

        if ("addProject".equals(action)) {
            String newProjectName = request.getParameter("newProjectName");
            if (newProjectName != null && !newProjectName.trim().isEmpty()) {
                Project newProject = new Project(newProjectName);
                newProject.addList(new TaskList("Por Hacer", newProject));
                newProject.addList(new TaskList("En Progreso", newProject));
                newProject.addList(new TaskList("Hecho", newProject));
                projects.put(nextProjectId, newProject);
                session.setAttribute(CURRENT_PROJECT_ID_SESSION_KEY, nextProjectId);
                nextProjectId++;
            }
        } else if ("selectProject".equals(action)) {
            String projectIdStr = request.getParameter("projectId");
            if (projectIdStr != null) {
                try {
                    int projectId = Integer.parseInt(projectIdStr);
                    session.setAttribute(CURRENT_PROJECT_ID_SESSION_KEY, projectId);
                } catch (NumberFormatException e) {
                    
                }
            }
        }

        session.setAttribute(PROJECTS_SESSION_KEY, projects);
        response.sendRedirect(request.getContextPath() + "/board");
    }
}
