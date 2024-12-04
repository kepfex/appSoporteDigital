package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.AsignacionDTO;
import modelo.dto.RolDTO;
import modelo.dto.SolicitudDTO;
import modelo.dto.UsuarioDTO;
import modelo.facade.AsignacionFacade;
import modelo.facade.RolFacade;
import modelo.facade.SolicitudFacade;
import modelo.facade.UsuarioFacade;
import modelo.services.Helper;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class SolicitudAsignacionesBean implements Serializable {

    private RolDTO rolDTO;
    private List<UsuarioDTO> colaboradores; // Lista para la tabla dt-colaboradores
    private SolicitudDTO solicitudDTO;
    private List<AsignacionDTO> asignacionesDTO; // Lista para dt-asignaciones
    private AsignacionDTO nuevaAsignacion; // Objeto temporal para la asignación

    private UsuarioFacade usuarioFacade;
    private SolicitudFacade solicitudFacade;
    private RolFacade rolFacde;
    private AsignacionFacade asignacionFacade;

    private DateTimeFormatter formatterFecha;
    private DateTimeFormatter formatterHora;
    private Integer idSolicitud;
    private Integer idCoordinadorActivo; // Guardará el ID del coordinador activo
    private Boolean mostrarFormulario;

    @PostConstruct
    public void init() {
        this.rolDTO = new RolDTO();
        this.rolFacde = new RolFacade();
        this.usuarioFacade = new UsuarioFacade();
        this.solicitudFacade = new SolicitudFacade();
        this.asignacionFacade = new AsignacionFacade();
        asignacionesDTO = new ArrayList<>();
        nuevaAsignacion = new AsignacionDTO(); // Inicializa un objeto vacío
        String idSolicitudURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitud");

        try {
            if (idSolicitudURL != null && idSolicitudURL.matches("\\d+")) { // Solo números positivos
                this.formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.formatterHora = DateTimeFormatter.ofPattern("hh:mm a");
                idSolicitud = Integer.valueOf(idSolicitudURL);

                solicitudDTO = solicitudFacade.buscarById(idSolicitud);
                if (solicitudDTO != null) {
                    this.mostrarFormulario = true;
                } else {
                    System.err.println("No se puedo buscar solicitud por id");
                }

            } else {
                this.mostrarFormulario = false;
                System.out.println("Id del usuario no recibido o no válido.");
            }
        } catch (Exception e) {
//            mostrarFormulario = false;
            System.out.println("Error procesando el ID de la solictud: " + e.getMessage());
        }

    }

    public void buscarColaborador() {
        colaboradores = usuarioFacade.listarUsuarioPorRol(rolDTO.getId(), "E");

    }

    // Litar Roles para colaborades de empresa
    public List<RolDTO> getListaRoles() {
        return rolFacde.listarRoles();
    }

    public void agregarAsignacion(UsuarioDTO colaborador) {
        // Llenar los datos necesarios en la nueva asignación
        nuevaAsignacion = new AsignacionDTO();
        nuevaAsignacion.setIdSolicitud(solicitudDTO.getId());
        nuevaAsignacion.setIdUsuario(colaborador.getId());
        nuevaAsignacion.setNumeroDNI(colaborador.getNumeroDeDocumentoDeIdentidad());
        nuevaAsignacion.setNombres(colaborador.getNombres());
        nuevaAsignacion.setApellidos(colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
        nuevaAsignacion.setNombreRol(colaborador.getRol().getNombre());

        if (!asignacionesDTO.contains(nuevaAsignacion)) {
            // Agregar el objeto al listado
            asignacionesDTO.add(nuevaAsignacion);
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Colaborador agregado", colaborador.getNombres() + " ha sido asignado.");
            // Resetear el objeto temporal (opcional, si se reusará)
            nuevaAsignacion = new AsignacionDTO();
        } else {
            Helper.addContextMessage(FacesMessage.SEVERITY_WARN, "Ya asignado", "El colaborador ya está en la lista.");
        }

    }

    public void removerAsignacion(AsignacionDTO asignacion) {
        asignacionesDTO.remove(asignacion);
        Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Colaborador eliminado", asignacion.getNombres() + " ha sido eliminado.");
    }

    public void toggleCoordinador(AsignacionDTO asignado) {
        if (asignado.isCoordinador()) {
            idCoordinadorActivo = asignado.getIdUsuario(); // Activar y guardar ID
        } else {
            idCoordinadorActivo = null; // Desactivar
        }

        // Actualizar el estado de los demás asignados
        for (AsignacionDTO a : asignacionesDTO) {
            if (a.getIdUsuario() != idCoordinadorActivo) {
                a.setCoordinador(false);
            }
        }
    }

    public void guardarAsignaciones() {
        // Validación: Asegurarse de que hay asignaciones
        if (asignacionesDTO == null || asignacionesDTO.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertencia", "Debe asignar al menos un colaborador."));
            return;
        }

        // Validación: Asegurar que haya un coordinador
        boolean hayCoordinador = asignacionesDTO.stream().anyMatch(AsignacionDTO::isCoordinador);
        if (!hayCoordinador) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Advertencia", "Debe asignar a un coordinador."));
            return;
        }

        boolean resultado = asignacionFacade.insertar(asignacionesDTO);
        // todo ok insertar en la bd
        if (resultado) {
            Helper.addFlashMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Se asignaron correctamente");
            Helper.redirectTo("/soporte/solicitudes/asignadas");
            System.out.println("Todas las asignaciones fueron insertadas correctamente.");
        } else {
            System.err.println("Ocurrieron errores al insertar algunas asignaciones.");
        }
    }
}
