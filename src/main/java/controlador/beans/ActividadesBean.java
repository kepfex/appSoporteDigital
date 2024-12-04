package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.AsignacionDTO;
import modelo.dto.SolicitudDTO;
import modelo.dto.TrabajoDTO;
import modelo.dto.UsuarioDTO;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeEmpresa;
import modelo.eums.EstadoSolicitud;
import modelo.facade.AsignacionFacade;
import modelo.facade.MailServiceFacade;
import modelo.facade.SolicitudFacade;
import modelo.facade.TrabajoFacade;
import modelo.facade.UsuarioFacade;
import modelo.services.FechaUtil;
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
public class ActividadesBean implements Serializable {

    private Integer idSolicitud;
    private Integer idUsuario;
    private Boolean mostrarFormulario;
    private Boolean coordinador;
    private Boolean existeFechaHoraAtencion;
//    private Boolean existeFechaHoraTerminoActividad;

    private SolicitudDTO solicitudSeleccionado;
    private SolicitudFacade solicitudFacade;
    private UsuarioDTO usuarioSeleccionado;
    private UsuarioFacade usuarioFacade;
    private AsignacionDTO asignacionSeleccionada;
    private AsignacionFacade asignacionFacade;
    private MailServiceFacade mailServiceFacade;

    private List<TrabajoDTO> trabajosDTO;
    private TrabajoDTO trabajoDTO;
    private TrabajoFacade trabajoFacade;

    @PostConstruct
    public void init() {
        this.solicitudFacade = new SolicitudFacade();
        this.usuarioFacade = new UsuarioFacade();
        this.asignacionFacade = new AsignacionFacade();
        this.trabajoFacade = new TrabajoFacade();
        this.mailServiceFacade = new MailServiceFacade();

        try {
            String idSolicitudURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitud");
            String idUsuarioURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUsuario");

            if (idSolicitudURL != null && idSolicitudURL.matches("\\d+")
                    && idUsuarioURL != null && idUsuarioURL.matches("\\d+")) { // Solo números positivos
                this.idSolicitud = Integer.valueOf(idSolicitudURL);
                this.idUsuario = Integer.valueOf(idUsuarioURL);

                this.solicitudSeleccionado = buscarSolicitud(idSolicitud);
                this.usuarioSeleccionado = buscarUsuario(idUsuario);
                this.asignacionSeleccionada = buscarAsignacion(idSolicitud, idUsuario);
                if (asignacionSeleccionada == null) {
                    mostrarFormulario = false;
                    return;
                }

                this.mostrarFormulario = true;
                this.coordinador = asignacionSeleccionada.isCoordinador();
                this.existeFechaHoraAtencion = solicitudSeleccionado.getFechaHoraInicioAtencion() == null;

                this.trabajosDTO = listarTrabajos();

            } else {
                this.mostrarFormulario = false;
                System.out.println("Id del usuario o solicitud, no recibido o no válido.");
            }
        } catch (Exception e) {
            this.mostrarFormulario = false;
            System.out.println("Error al procesar el ID de la solictud o usuario: " + e.getMessage());
        }
    }

    public void openNew() {
        this.trabajoDTO = new TrabajoDTO();
    }

    public SolicitudDTO buscarSolicitud(int idSolicitud) {
        return solicitudFacade.buscarById(idSolicitud);
    }

    public UsuarioDTO buscarUsuario(int idUsuario) {
        return usuarioFacade.buscarUsuarioByIdAndTipo(idUsuario, "E");
    }

    public AsignacionDTO buscarAsignacion(int idSolicitud, int idUsuario) {
        return asignacionFacade.buscarAsignacionByIdSolicitudAndIdUsuario(idSolicitud, idUsuario);
    }

    public void actualizarFechaHoraInicioDeAtencion() {

        if (asignacionFacade.iniciarAtencion(solicitudSeleccionado.getId(), usuarioSeleccionado.getId())) {
            this.existeFechaHoraAtencion = false; // Actualizar el estado del atributo
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Atención iniciada", "La atención ha sido iniciada.");
        } else {
            Helper.addContextMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo iniciar la atención.");
        }
    }

    public List<TrabajoDTO> listarTrabajos() {
        return trabajoFacade.listarTrabajosBySolicitudAndUsuario(solicitudSeleccionado.getId(), usuarioSeleccionado.getId());
    }

    public void guardarActividad() {
        boolean ejecutadoExitosamente = false;
        if (trabajoDTO.getId() == 0) {
            trabajoDTO.setFechaHoraInicioActividad(FechaUtil.obtenerFechaHoraActual());
            trabajoDTO.setSolicitud(Solicitud.builder().id(solicitudSeleccionado.getId()).build());
            trabajoDTO.setUsuario(UsuarioDeEmpresa.builder().id(usuarioSeleccionado.getId()).build());
            ejecutadoExitosamente = trabajoFacade.insertar(trabajoDTO);

        } else {

        }

        if (ejecutadoExitosamente) {
            // Actualizar la lista
            this.trabajosDTO = listarTrabajos();
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Actividad resgistrada.");
            PrimeFaces.current().executeScript("PF('manageActividadDialog').hide()");
            PrimeFaces.current().ajax().update("form2:messages", "form2:dt-actividades");
        } else {
            Helper.addContextMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear la actividad");
        }
    }

    public void finalizarActividad() {
        if (trabajoFacade.finalizarActividad(trabajoDTO)) {
            this.trabajosDTO = listarTrabajos();
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Actividad finalizada.");
            PrimeFaces.current().ajax().update("form2:messages", "form2:dt-actividades");
        }
    }

    public void finalizarAtencion() {
        List<TrabajoDTO> trabajos = trabajoFacade.listarTrabajosBySolicitudo(solicitudSeleccionado.getId());

        if (trabajos.stream().anyMatch(t -> t.getFechaHoraTerminoActividad() == null)) {
            Helper.addContextMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Todos los usuarios asignados deben de finalizar sus actividades.");
            return;
        }
        LocalDateTime fechaHoraActual = FechaUtil.obtenerFechaHoraActual();
        if (asignacionFacade.finalizarAtencion(solicitudSeleccionado, fechaHoraActual) && solicitudFacade.finalizarAtencion(solicitudSeleccionado, fechaHoraActual)) {
            // Actualizamos estado a T(Atendida)
            solicitudFacade.actualizarEstado(solicitudSeleccionado, EstadoSolicitud.ATENDIDA);
            // Enviar mensaje
            mailServiceFacade.enviarEmailFinalizacionSolicitud(solicitudSeleccionado, fechaHoraActual);
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Exito", "¡Solicitud Atendida! Se envió un mensaje al correo de usuario.");
        } else {
            Helper.addContextMessage(FacesMessage.SEVERITY_ERROR, "Error", "Hubo algún error al finalizar esta solictud.");
        }

    }
}
