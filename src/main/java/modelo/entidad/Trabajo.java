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
public class Trabajo {
    private int id;
    private LocalDateTime fechaHoraInicioActividad;
    private LocalDateTime fechaHoraTerminoActividad;
    private String descripción;
    private Solicitud solicitud;
    private UsuarioDeEmpresa usuario;
}
