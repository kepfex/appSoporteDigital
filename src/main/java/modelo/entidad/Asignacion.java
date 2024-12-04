package modelo.entidad;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author KEVIN
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignacion {
    private LocalDateTime fechaHoraAsignacion;
    private LocalDateTime fechaHoraInicioAtencion;
    private LocalDateTime fechaHoraTerminoAtencion;
    private boolean isCoordinador;
    private Solicitud solicitud;
    private UsuarioDeEmpresa usuario;
}
