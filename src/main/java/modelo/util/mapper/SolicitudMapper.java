package modelo.util.mapper;

import modelo.dto.SolicitudDTO;
import modelo.entidad.Solicitud;

/**
 *
 * @author KEVIN
 */
public class SolicitudMapper {
    /**
     * Convierte una entidad Solicitud a un DTO de Solicitud.
     *
     * @param solicitud La entidad Solicitud.
     * @return Un objeto SolicitudDTO con los datos mapeados.
     */
    public static SolicitudDTO solicitudToDTO(Solicitud solicitud) {
        if (solicitud == null) {
            return null;
        }

        return SolicitudDTO.builder()
                .id(solicitud.getId())
                .year(solicitud.getYear())
                .mes(solicitud.getMes())
                .numeroCorrelativo(solicitud.getNumeroCorrelativo())
                .fechaHora(solicitud.getFechaHora())
                .fechaHoraAsignacion(solicitud.getFechaHoraAsignacion())
                .fechaHoraInicioAtencion(solicitud.getFechaHoraInicioAtencion())
                .fechaHoraTerminoAtencion(solicitud.getFechaHoraTerminoAtencion())
                .motivo(solicitud.getMotivo())
                .estado(solicitud.getEstado())
                .tipo(solicitud.getTipo())
                .usuario(solicitud.getUsuario())
                .build();
    }
    
    /**
     * Convierte un DTO de Solicitud a una entidad Solicitud.
     *
     * @param solicitudDTO El DTO de Solicitud.
     * @return Una entidad Solicitud con los datos mapeados.
     */
    public static Solicitud toSolicitud(SolicitudDTO solicitudDTO) {
        if (solicitudDTO == null) {
            return null;
        }

        return Solicitud.builder()
                .id(solicitudDTO.getId())
                .year(solicitudDTO.getYear())
                .mes(solicitudDTO.getMes())
                .numeroCorrelativo(solicitudDTO.getNumeroCorrelativo())
                .fechaHora(solicitudDTO.getFechaHora())
                .fechaHoraAsignacion(solicitudDTO.getFechaHoraAsignacion())
                .fechaHoraInicioAtencion(solicitudDTO.getFechaHoraInicioAtencion())
                .fechaHoraTerminoAtencion(solicitudDTO.getFechaHoraTerminoAtencion())
                .motivo(solicitudDTO.getMotivo())
                .estado(solicitudDTO.getEstado())
                .tipo(solicitudDTO.getTipo())
                .usuario(solicitudDTO.getUsuario())
                .build();
    }
}
