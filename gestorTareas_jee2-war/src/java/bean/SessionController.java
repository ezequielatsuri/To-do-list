/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;


import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("sessionController")
@RequestScoped
public class SessionController {

    public String checkSession() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null && session.getAttribute("loggedInUser") != null) {
            return "/board?faces-redirect=true"; // Redirigir al tablero si el usuario está logueado
        } else {
            return "/login?faces-redirect=true"; // Redirigir al login si el usuario no está logueado
        }
    }
}