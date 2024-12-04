package controlador.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.UsuarioLogeadoDTO;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped
@Getter
@Setter
public class ValidacionTipoUsuarioBean implements Serializable {

    public void validarAccesoCliente() throws IOException {
        validarAcceso("C", "/appSoporteDigital/error/404");
    }

    public void validarAccesoEmpresa() throws IOException {
        validarAcceso("E", "/appSoporteDigital/error/404");
    }

    public void validarAccesoSoporte() throws IOException {
        validarAcceso("S", "/appSoporteDigital/error/404");
    }

    private void validarAcceso(String tipoEsperado, String paginaError) throws IOException {
        UsuarioLogeadoDTO usuario = (UsuarioLogeadoDTO) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuarioLogeadoDTO");
        if (usuario == null || !tipoEsperado.equals(usuario.getTipoUsuario().getCodigo())) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(paginaError);
        }
    }

    public void cerrarSesion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Invalidar la sesión actual
        FacesContext.getCurrentInstance().getExternalContext().redirect("/appSoporteDigital/login"); // Redirigir a la página de inicio de sesión
    }
}
