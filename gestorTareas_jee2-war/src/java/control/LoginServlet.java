/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control;

import datos.User;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;


/**
 *
 * @author ezequ
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USER_SESSION_KEY = "loggedInUser";
    private static final Map<String, String> USERS = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // Inicializamos algunos usuarios de ejemplo
        USERS.put("usuario1", "clave1");
        USERS.put("usuario2", "clave2");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar el formulario de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (USERS.containsKey(username) && USERS.get(username).equals(password)) {
            // Autenticación exitosa
            HttpSession session = request.getSession();
            User loggedInUser = new User(username, null);
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            response.sendRedirect(request.getContextPath() + "/board"); // Redirigir al tablero
        } else {
            // Autenticación fallida
            request.setAttribute("errorMessage", "Credenciales incorrectas.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}

