package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.UsuarioLogeadoDTO;
import modelo.facade.UsuarioFacade;
import modelo.services.Helper;

/**
 *
 * @author KEVIN
 */
@Named
@SessionScoped
@Getter
@Setter
public class LoginBean implements Serializable {

    private String correo;
    private String password;
    private UsuarioFacade usuarioFacade;

    @PostConstruct
    public void init() {
        this.usuarioFacade = new UsuarioFacade();
    }

    public String login() throws IOException {
        // Validar si los campos están vacíos
        if (correo == null || correo.isEmpty()) {
            Helper.addMessage("loginForm:email", FacesMessage.SEVERITY_ERROR, "El correo electrónico es requerido.", null); // Asignar el mensaje al input de correo
        }
        if (password == null || password.isEmpty()) {
            Helper.addMessage("loginForm:password", FacesMessage.SEVERITY_ERROR, "La contraseña es requerida.", null); // Asignar el mensaje al input de password
        }
        // Si hay errores, no continuar
        if (!FacesContext.getCurrentInstance().getMessageList().isEmpty()) {
            return null; // Detener el proceso si hay mensajes de error
        }

        try {
            UsuarioLogeadoDTO usuarioLogeadoDTO = usuarioFacade.autenticar(correo, password);
            // Guardar el usuario logeado en la sesión de JSF
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogeadoDTO", usuarioLogeadoDTO);

            // Redirigir al dashboard correspondiente
            FacesContext.getCurrentInstance().getExternalContext().redirect("/appSoporteDigital" + usuarioLogeadoDTO.getPathURL());
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null;
        }
    }
}
