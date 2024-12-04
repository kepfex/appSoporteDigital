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
public class RolDTO {
    private int id;
    private String nombre;
}
