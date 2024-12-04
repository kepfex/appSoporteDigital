package modelo.entidad;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 *
 * @author KEVIN
 */

@SuperBuilder
@Getter
@Setter
@ToString
public class UsuarioDeCliente extends Usuario {
    private Cliente cliente;

    public UsuarioDeCliente(int id) {
        super(id);
    }
    
    
}
