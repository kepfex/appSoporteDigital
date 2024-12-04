package modelo.facade;

import java.util.List;
import java.util.stream.Collectors;
import modelo.dao.RolDAO;
import modelo.dto.RolDTO;
import modelo.entidad.Rol;

/**
 *
 * @author KEVIN
 */
public class RolFacade {
    private final RolDAO rolDAO;

    public RolFacade() {
        this.rolDAO = new RolDAO();
    }
    
    public List<RolDTO> listarRoles() {
        List<Rol> rol = rolDAO.findAll();
        return rol.stream()
                .map(this::convertirToRolDTO)
                .collect(Collectors.toList());
    }
    
    private RolDTO convertirToRolDTO(Rol rol) {
        return RolDTO.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .build();
    }
}
