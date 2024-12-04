package modelo.services;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;

/**
 *
 * @author KEVIN
 */
public class Helper {

    public static void redirectTo(String url) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + url);
        } catch (IOException e) {
            System.err.println("Error al redirigir: " + e.getMessage());
        }
    }

    /**
     * Añade un mensaje informativo al contexto flash para redirecciones.
     *
     * @param severity Severidad del mensaje (INFO, WARN, ERROR, FATAL)
     * @param titulo Resumen del mensaje
     * @param mensaje Detalle del mensaje
     */
    public static void addFlashMessage(FacesMessage.Severity severity, String titulo, String mensaje) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            FacesMessage message = new FacesMessage(severity, titulo, mensaje);
            facesContext.addMessage(null, message);
        }
    }

    /**
     * Añade un mensaje al contexto actual.
     *
     * @param severity Severidad del mensaje (INFO, WARN, ERROR, FATAL)
     * @param titulo Resumen del mensaje
     * @param mensaje Detalle del mensaje
     */
    public static void addContextMessage(FacesMessage.Severity severity, String titulo, String mensaje) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            FacesMessage message = new FacesMessage(severity, titulo, mensaje);
            facesContext.addMessage(null, message);
        }
    }
    
    public static void addMessage(String clientId, FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
}
