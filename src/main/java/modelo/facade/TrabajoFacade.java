package modelo.facade;

import java.util.List;
import java.util.stream.Collectors;
import modelo.dao.TrabajoDAO;
import modelo.dto.TrabajoDTO;
import modelo.entidad.Trabajo;
import modelo.services.FechaUtil;
import modelo.util.mapper.TrabajoMapper;

/**
 *
 * @author KEVIN
 */
public class TrabajoFacade {
    private final TrabajoDAO trabajoDAO;
    
    public TrabajoFacade() {
        this.trabajoDAO = new TrabajoDAO();
    }
    
    public List<TrabajoDTO> listarTrabajosBySolicitudAndUsuario(int idSolicitud, int idUsuario){
        List<Trabajo> trabajos = trabajoDAO.findAllByTwoColums("idSolicitud", idSolicitud, "idUsuario", idUsuario);
        return trabajos.stream()
                .map(TrabajoMapper::trabajoToDTO)
                .collect(Collectors.toList());
    }
    
    public List<TrabajoDTO> listarTrabajosBySolicitudo(int idSolicitud){
        List<Trabajo> trabajos = trabajoDAO.findAllByColum("idSolicitud", idSolicitud);
        return trabajos.stream()
                .map(TrabajoMapper::trabajoToDTO)
                .collect(Collectors.toList());
    }
    
    public boolean insertar(TrabajoDTO trabajoDTO){
        Trabajo trabajo = TrabajoMapper.toTrabajo(trabajoDTO);
        return trabajoDAO.crear(trabajo);
    }
    public boolean finalizarActividad(TrabajoDTO trabajoDTO) {
        return trabajoDAO.updateCampo("fechahoratermino", FechaUtil.obtenerFechaHoraActual(), "idTrabajo", trabajoDTO.getId());
    }
    
}
