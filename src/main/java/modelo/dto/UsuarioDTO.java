package modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modelo.entidad.Cliente;
import modelo.entidad.Rol;

/**
 *
 * @author KEVIN
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsuarioDTO {
    private int id;
    private String tipoDeDocumentoDeIdentidad;
    private String numeroDeDocumentoDeIdentidad;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String correoElectronico;
    private String activo;
    private String tipo;
    private Cliente cliente;
    private Rol rol;
    private boolean confirmado;
    private String token;
}
