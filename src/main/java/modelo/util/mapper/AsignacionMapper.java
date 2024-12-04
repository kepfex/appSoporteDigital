package modelo.util.mapper;

import java.time.format.DateTimeFormatter;
import modelo.dto.AsignacionDTO;
import modelo.entidad.Asignacion;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeEmpresa;

/**
 *
 * @author KEVIN
 */
public class AsignacionMapper {
    /**
     * Convierte una entidad Asignacion a un DTO de Asignacion.
     *
     * @param asignacion La entidad Asignacion.
     * @return Un objeto AsignacionDTO con los datos mapeados.
     */
    public static AsignacionDTO asignacionToDTO(Asignacion asignacion) {
        if (asignacion == null) {
            return null;
        }
        
        DateTimeFormatter fechaFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return AsignacionDTO.builder()
                .idSolicitud(asignacion.getSolicitud() != null ? asignacion.getSolicitud().getId() : 0)
                .idUsuario(asignacion.getUsuario() != null ? asignacion.getUsuario().getId() : 0)
                .numeroDNI(asignacion.getUsuario() != null ? asignacion.getUsuario().getNumeroDeDocumentoDeIdentidad() : null)
                .nombres(asignacion.getUsuario() != null ? asignacion.getUsuario().getNombres() : null)
                .apellidos(asignacion.getUsuario() != null ? asignacion.getUsuario().getApellidoPaterno() +" "+ asignacion.getUsuario().getApellidoMaterno() : null)
                .nombreRol(asignacion.getUsuario() != null ? asignacion.getUsuario().getRol().getNombre() : null)
                .fechaHoraAsignacion(asignacion.getFechaHoraAsignacion())
                .fechaHoraInicioAtencion(asignacion.getFechaHoraInicioAtencion())
                .fechaHoraTerminoAtencion(asignacion.getFechaHoraTerminoAtencion())
                .isCoordinador(asignacion.isCoordinador())
                .codigoSolicitud(asignacion.getSolicitud().getYear()+asignacion.getSolicitud().getMes()+String.valueOf(asignacion.getSolicitud().getNumeroCorrelativo()))
                .razonSocial(asignacion.getSolicitud().getUsuario().getCliente().getRazonSocial())
                .fechaCreacion(asignacion.getSolicitud().getFechaHora().format(fechaFormatter)+" - "+asignacion.getSolicitud().getFechaHora().format(horaFormatter))
                .estado(asignacion.getSolicitud().getEstado())
                .build();
    }
    /**
     * Convierte un DTO de Asignacion a una entidad Asignacion.
     *
     * @param asignacionDTO El DTO de Asignacion.
     * @return Una entidad Asignacion con los datos mapeados.
     */
    public static Asignacion toAsignacion(AsignacionDTO asignacionDTO) {
        if (asignacionDTO == null) {
            return null;
        }
        return Asignacion.builder()
                .fechaHoraAsignacion(asignacionDTO.getFechaHoraAsignacion())
                .fechaHoraInicioAtencion(asignacionDTO.getFechaHoraInicioAtencion())
                .fechaHoraTerminoAtencion(asignacionDTO.getFechaHoraTerminoAtencion())
                .isCoordinador(asignacionDTO.isCoordinador())
                .solicitud(new Solicitud(asignacionDTO.getIdSolicitud()))
                .usuario(UsuarioDeEmpresa.builder().id(asignacionDTO.getIdUsuario()).build())
                .build();
    }
}
