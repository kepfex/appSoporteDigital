package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import modelo.facade.UsuarioFacade;
import modelo.services.Helper;
import modelo.services.PasswordUtils;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped
@Getter
@Setter

public class ConfirmarPasswordBean implements Serializable {

    private UsuarioFacade usuarioClienteFacade;

    private String token;
    private boolean tokenValido;

    private String password;

    @PostConstruct
    public void init() {
        usuarioClienteFacade = new UsuarioFacade();
        token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        if (token != null && !token.isEmpty()) {
            tokenValido = usuarioClienteFacade.isToken(token);
        } else {
            tokenValido = false;
            System.out.println("Token no recibido o vacío.");
        }
    }

    public void guardarPass() {
        // Generar el hash de la contraseña
        String hashedPassword = PasswordUtils.hashPassword(password);

        boolean isSaved = usuarioClienteFacade.actualizarPassword(hashedPassword, token);

        if (isSaved) {
            System.out.println("redirigiendo a login...");
            // Agrega un mensaje para el growl en la página de destino
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Contraseña creada correctamente."));
            
            Helper.redirectTo("/home");
        } else {
            System.out.println("Error al actualizar la contraseña.");
        }

        // Verificar la contraseña ingresada contra el hash
        //boolean esCorrecto = PasswordUtils.verifyPassword(password, hashedPassword);
//        System.out.println("¿La contraseña es correcta? " + esCorrecto);
    }

}
