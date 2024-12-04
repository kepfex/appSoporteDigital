package modelo.dto;

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
public class ClienteDTO {
    private int id;
    private String tipoDeDocumentoDeIdentidad;
    private String numeroDeDocumentoDeIdentidad;
    private String  razonSocial;
    private String direccion;
    private String telefono;
}
