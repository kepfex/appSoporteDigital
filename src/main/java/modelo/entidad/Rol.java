package modelo.entidad;

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
public class Rol {
    private int id;
    private String nombre;

    public Rol(int id) {
        this.id = id;
    }
}
