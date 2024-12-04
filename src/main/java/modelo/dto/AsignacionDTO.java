package modelo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.eums.EstadoSolicitud;

/**
 *
 * @author KEVIN
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionDTO {
    private int idSolicitud;
    private int idUsuario;
    private String numeroDNI;
    private String nombres;
    private String apellidos;
    private String nombreRol;
    private LocalDateTime fechaHoraAsignacion;
    private LocalDateTime fechaHoraInicioAtencion;
    private LocalDateTime fechaHoraTerminoAtencion;
    private boolean isCoordinador;
    private String codigoSolicitud;
    private String razonSocial;
    private String fechaCreacion;
    private EstadoSolicitud estado;
}
