package modelo.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.eums.TipoUsuario;

/**
 *
 * @author KEVIN
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogeadoDTO implements Serializable {
    private int idUsuario;
    private String nombres;
    private String apellidoPat;
    private String apellidoMat;
    private String correo;
    private TipoUsuario tipoUsuario;
    private String PathURL;
}
