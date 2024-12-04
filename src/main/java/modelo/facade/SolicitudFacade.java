package modelo.facade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import modelo.dao.SolicitudDAO;
import modelo.dto.SolicitudDTO;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeCliente;
import modelo.eums.EstadoSolicitud;
import modelo.services.FechaUtil;
import modelo.util.mapper.SolicitudMapper;

/**
 *
 * @author KEVIN
 */
public class SolicitudFacade {

    private final SolicitudDAO solicitudDAO;

    public SolicitudFacade() {
        this.solicitudDAO = new SolicitudDAO();
    }
    
    public boolean insertar(SolicitudDTO solicitudDTO, int idUsuario) {
        UsuarioDeCliente usuario = new UsuarioDeCliente(idUsuario);
        
        solicitudDTO.setYear(FechaUtil.obtenerAnioActual());
        solicitudDTO.setMes(FechaUtil.obtenerMesActual());
        solicitudDTO.setEstado(EstadoSolicitud.PENDIENTE);
        solicitudDTO.setUsuario(usuario);

        Solicitud solicitud = SolicitudMapper.toSolicitud(solicitudDTO);
        return solicitudDAO.crear(solicitud);
    }
    
    public List<SolicitudDTO> listarByUsuario(int idUsuario){
        List<Solicitud> solicitudes = solicitudDAO.findAllByColum("idUsuario", idUsuario);
        return solicitudes.stream()
                .map(SolicitudMapper::solicitudToDTO)
                .collect(Collectors.toList());
    }
    public List<SolicitudDTO> listarAll(){
        List<Solicitud> solicitudes = solicitudDAO.findAll();
        return solicitudes.stream()
                .map(SolicitudMapper::solicitudToDTO)
                .collect(Collectors.toList());
    }
    
    public SolicitudDTO buscarById(int id) {
        Solicitud solicitud = solicitudDAO.findById(id);
        return SolicitudMapper.solicitudToDTO(solicitud);
    }
    
    public boolean finalizarAtencion(SolicitudDTO solicitud, LocalDateTime fechaHoraFin) {
        return solicitudDAO.updateCampo("fechahoraterminoatencion", fechaHoraFin, "idSolicitud", solicitud.getId());
    }
    public boolean actualizarEstado(SolicitudDTO solicitud, EstadoSolicitud estado) {
        System.out.println(estado.getCodigo());
        return solicitudDAO.updateCampo("estado", estado.getCodigo(), "idSolicitud", solicitud.getId());
    }
}
