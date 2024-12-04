package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.SolicitudDTO;
import modelo.dto.UsuarioLogeadoDTO;
import modelo.eums.TipoSolicitud;
import modelo.facade.SolicitudFacade;
import modelo.services.Helper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class SolicitudBean implements Serializable {

    private SolicitudDTO solicitud;
    private List<SolicitudDTO> listaSolicitudes;
    private SolicitudFacade solicitudFacade;

    private DateTimeFormatter formatterFecha;
    private DateTimeFormatter formatterHora;
    private UsuarioLogeadoDTO usuarioLogeado;

    @PostConstruct
    public void init() {
        solicitudFacade = new SolicitudFacade();
        openNew();

        this.formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.formatterHora = DateTimeFormatter.ofPattern("hh:mm a");

        this.usuarioLogeado = getUsuarioLogeado();
        listarSolicitudes(usuarioLogeado);
    }

    public void openNew() {
        this.solicitud = new SolicitudDTO();
    }

    public void guardarSolicitud() {
        boolean ejecutadoExitosamente = false;
        if (solicitud.getId() == 0) {
            ejecutadoExitosamente = solicitudFacade.insertar(solicitud, usuarioLogeado.getIdUsuario());

        } else {

        }

        if (ejecutadoExitosamente) {
            // Actualizar la lista de solicitudes
            listarSolicitudes(usuarioLogeado);
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Solicitud enviada");
            PrimeFaces.current().executeScript("PF('manageSolicitudDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-solicitudes");
        }
    }

    public TipoSolicitud[] getTiposSolicitud() {
        return TipoSolicitud.values();
    }

    private UsuarioLogeadoDTO getUsuarioLogeado() {
        // Obtener el usuario desde la sesión
        return (UsuarioLogeadoDTO) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuarioLogeadoDTO");
    }

    private void listarSolicitudes(UsuarioLogeadoDTO usuario) {
        if (usuarioLogeado.getTipoUsuario().name().equals("SOPORTE")) {
            this.listaSolicitudes = solicitudFacade.listarAll();
            return;
        }
        if (usuarioLogeado.getTipoUsuario().name().equals("CLIENTE")) {
            this.listaSolicitudes = solicitudFacade.listarByUsuario(usuario.getIdUsuario());
        }
    }

    public List<SolicitudDTO> filtrarSolicitudesPorEstado(String estado) {
        if (listaSolicitudes != null && !listaSolicitudes.isEmpty()) {
            return listaSolicitudes.stream()
                    .filter(solicitudes -> solicitudes.getEstado().name().equals(estado))
                    .toList(); // Devuelve una lista inmutable
        }
        return List.of(); // Devuelve una lista vacía si no hay datos
    }
}
