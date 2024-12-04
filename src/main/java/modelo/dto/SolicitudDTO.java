package modelo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.entidad.UsuarioDeCliente;
import modelo.eums.EstadoSolicitud;
import modelo.eums.TipoSolicitud;

/**
 *
 * @author KEVIN
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDTO {
    private int id;
    private int year;
    private int mes;
    private int numeroCorrelativo;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaHoraAsignacion;
    private LocalDateTime fechaHoraInicioAtencion;
    private LocalDateTime fechaHoraTerminoAtencion;
    private String motivo;
    private EstadoSolicitud estado; //Puede ser: P(Pendiente), A(Asignada), R(Proceso), T(Atendida)
    private TipoSolicitud tipo; //Puede ser: E(Error), C(Capacitación), R(Requerimiento);
    private UsuarioDeCliente usuario;

    public SolicitudDTO(int id) {
        this.id = id;
    }
}
