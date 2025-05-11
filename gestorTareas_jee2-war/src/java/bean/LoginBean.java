/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

import datos.User;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private User loggedInUser;

    // Simulación de la base de datos de usuarios
    private static final Map<String, String> USERS = new HashMap<>();

    public LoginBean() {
        // Inicializamos algunos usuarios de ejemplo (esto en una aplicación real vendría de una base de datos)
        USERS.put("usuario1", "clave1");
        USERS.put("usuario2", "clave2");
    }

    public String login() {
        if (USERS.containsKey(username) && USERS.get(username).equals(password)) {
            loggedInUser = new User(username, null); // No necesitamos guardar la contraseña en la sesión
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("loggedInUser", loggedInUser);
            return "/board?faces-redirect=true"; // Navegación a la página del tablero
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de autenticación", "Credenciales incorrectas."));
            return null; // Permanecer en la página de login
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        loggedInUser = null;
        return "/login?faces-redirect=true"; // Redirigir a la página de login
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}