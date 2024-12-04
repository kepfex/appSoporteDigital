package modelo.entidad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import modelo.eums.EstadoEntidad;
import modelo.eums.TipoDocumentoIdentidad;
import modelo.eums.TipoUsuario;

/**
 *
 * @author KEVIN
 */

@SuperBuilder
@Getter
@Setter
@ToString
public class Usuario {
    private int id;
    private TipoDocumentoIdentidad tipoDeDocumentoDeIdentidad;
    private String numeroDeDocumentoDeIdentidad;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String correoElectronico;
    private String password;
    private EstadoEntidad activo;
    private TipoUsuario tipo;
    private boolean confirmado;
    private String token;

    public Usuario(int id) {
        this.id = id;
    }
   
}
