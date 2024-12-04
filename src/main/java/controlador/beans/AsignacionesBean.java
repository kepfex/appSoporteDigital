package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.AsignacionDTO;
import modelo.dto.UsuarioLogeadoDTO;
import modelo.facade.AsignacionFacade;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class AsignacionesBean implements Serializable {

    private List<AsignacionDTO> asignacionesDTO;
    private AsignacionFacade asignacionFacade;

    private DateTimeFormatter formatterFecha;
    private DateTimeFormatter formatterHora;
    private UsuarioLogeadoDTO usuarioLogeado;

    private List<AsignacionDTO> asignacionesAtendidas; // Solo con estado "Atendida"
    private List<AsignacionDTO> asignacionesAsignadas; // Todos los demás estados

    @PostConstruct
    public void init() {
        this.asignacionFacade = new AsignacionFacade();
        this.formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.formatterHora = DateTimeFormatter.ofPattern("hh:mm a");

        this.usuarioLogeado = getUsuarioLogeado();
        getlistarAsignaciones();
    }

    public void getlistarAsignaciones() {
        asignacionesDTO = asignacionFacade.listarAsignacionesByUsuario(usuarioLogeado.getIdUsuario());

        // Filtrar las asignaciones por estado
        asignacionesAtendidas = asignacionesDTO.stream()
                .filter(asignacion -> "T".equalsIgnoreCase(asignacion.getEstado().getCodigo()))
                .toList();

        asignacionesAsignadas = asignacionesDTO.stream()
                .filter(asignacion -> !"T".equalsIgnoreCase(asignacion.getEstado().getCodigo()))
                .toList();
    }

    private UsuarioLogeadoDTO getUsuarioLogeado() {
        // Obtener el usuario desde la sesión
        return (UsuarioLogeadoDTO) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuarioLogeadoDTO");
    }

}
