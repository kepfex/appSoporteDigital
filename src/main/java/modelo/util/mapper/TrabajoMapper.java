package modelo.util.mapper;

import modelo.dto.TrabajoDTO;
import modelo.entidad.Trabajo;

/**
 * Mapper para convertir entre entidades Trabajo y DTOs de Trabajo.
 * 
 * @author KEVIN
 */
public class TrabajoMapper {
    /**
     * Convierte una entidad Trabajo a un DTO de Trabajo.
     *
     * @param trabajo La entidad Trabajo.
     * @return Un objeto TrabajoDTO con los datos mapeados.
     */
    public static TrabajoDTO trabajoToDTO(Trabajo trabajo) {
        if (trabajo == null) {
            return null;
        }

        return TrabajoDTO.builder()
                .id(trabajo.getId())
                .fechaHoraInicioActividad(trabajo.getFechaHoraInicioActividad())
                .fechaHoraTerminoActividad(trabajo.getFechaHoraTerminoActividad())
                .descripción(trabajo.getDescripción())
                .solicitud(trabajo.getSolicitud()) // Aquí puedes usar un mapper si es necesario
                .usuario(trabajo.getUsuario()) // Aquí también podrías usar un mapper si aplica
                .build();
    }

    /**
     * Convierte un DTO de Trabajo a una entidad Trabajo.
     *
     * @param trabajoDTO El DTO de Trabajo.
     * @return Una entidad Trabajo con los datos mapeados.
     */
    public static Trabajo toTrabajo(TrabajoDTO trabajoDTO) {
        if (trabajoDTO == null) {
            return null;
        }

        return Trabajo.builder()
                .id(trabajoDTO.getId())
                .fechaHoraInicioActividad(trabajoDTO.getFechaHoraInicioActividad())
                .fechaHoraTerminoActividad(trabajoDTO.getFechaHoraTerminoActividad())
                .descripción(trabajoDTO.getDescripción())
                .solicitud(trabajoDTO.getSolicitud()) // Igual que arriba, puedes usar un mapper si necesitas convertir el objeto
                .usuario(trabajoDTO.getUsuario()) // Mapper opcional si aplica
                .build();
    }
}
