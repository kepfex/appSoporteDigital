package modelo.facade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import modelo.dao.AsignacionDAO;
import modelo.dao.SolicitudDAO;
import modelo.dto.AsignacionDTO;
import modelo.dto.SolicitudDTO;
import modelo.entidad.Asignacion;
import modelo.services.FechaUtil;
import modelo.util.mapper.AsignacionMapper;

/**
 *
 * @author KEVIN
 */
public class AsignacionFacade {

    private final AsignacionDAO asignacionDAO;
    private final SolicitudDAO solicitudDAO;

    public AsignacionFacade() {
        this.asignacionDAO = new AsignacionDAO();
        this.solicitudDAO = new SolicitudDAO();
    }

    public boolean insertar(List<AsignacionDTO> asignacionesDTO) {
        // Obtén el idSolicitud del primer elemento
        int idSolicitud = asignacionesDTO.get(0).getIdSolicitud();

        System.out.println(idSolicitud);

        // Mapear DTO a entidades
        List<Asignacion> asignaciones = asignacionesDTO.stream()
                .map(AsignacionMapper::toAsignacion)
                .collect(Collectors.toList());

        boolean exito = true;

        if (!solicitudDAO.updateCampo("fechahoraasignacion", FechaUtil.obtenerFechaHoraActual(), "idSolicitud", idSolicitud)) {
            System.err.println("Error al actualizar la fecha de asignacion a solicitud:");
            return false; // Marca como fallido
        }
        
        if (!solicitudDAO.updateCampo("estado", "A", "idSolicitud", idSolicitud)) { 
            System.err.println("Error al actualizar estado a solicitud:");
            return false; // Marca como fallido
        }
        
        for (Asignacion asignacion : asignaciones) {
            if (!asignacionDAO.crear(asignacion)) {
                exito = false; // Marca como fallido si alguna inserción no tiene éxito
                System.err.println("Error al insertar la asignación: " + asignacion);
            }
        }

        return exito; // Retorna true solo si todas las inserciones tuvieron éxito
    }
    
    public List<AsignacionDTO> listarAsignacionesByUsuario(int idUsuario){
        List<Asignacion> asignaciones = asignacionDAO.findAllByColum("idUsuario", idUsuario);
        
        return asignaciones.stream()
                .map(AsignacionMapper::asignacionToDTO)
                .collect(Collectors.toList());
    }
    
    public AsignacionDTO buscarAsignacionByIdSolicitudAndIdUsuario(int idSolicitud, int idUsuario){
        Asignacion asignacion =  asignacionDAO.findByTwoColumns("idSolicitud", idSolicitud, "idUsuario", idUsuario);
        return AsignacionMapper.asignacionToDTO(asignacion);
    }
    
    public boolean iniciarAtencion(int idSolicitud, int idUsuario) {

        if (!asignacionDAO.updateCampo("fechahorainicioatencion", FechaUtil.obtenerFechaHoraActual(), "idUsuario", idUsuario)) {
            System.err.println("Error al actualizar la fecha de atencion de asignacion: ");
            return false; // Marca como fallido
        }
        
        if (!solicitudDAO.updateCampo("fechahorainicioatencion", FechaUtil.obtenerFechaHoraActual(), "idSolicitud", idSolicitud)) {
            System.err.println("Error al actualizar la fecha de atencion de solicitud:");
            return false; // Marca como fallido
        }
        
        if (!solicitudDAO.updateCampo("estado", "R", "idSolicitud", idSolicitud)) { 
            System.err.println("Error al actualizar estado a solicitud:");
            return false; // Marca como fallido
        }
        return true;
    }
    public boolean finalizarAtencion(SolicitudDTO solicitud, LocalDateTime fechaHoaraFin){
        return asignacionDAO.updateCampo("fechahoraterminoatencion", fechaHoaraFin, "idSolicitud", solicitud.getId());
    }
}
