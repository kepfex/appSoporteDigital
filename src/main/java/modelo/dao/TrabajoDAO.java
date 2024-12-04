package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import modelo.entidad.Solicitud;
import modelo.entidad.Trabajo;
import modelo.entidad.UsuarioDeEmpresa;

/**
 *
 * @author KEVIN
 */
public class TrabajoDAO extends AbstractDAO<Trabajo>{

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final UsuarioDeEmpresaDAO usuarioEmpresaDAO = new UsuarioDeEmpresaDAO();
    
    @Override
    protected Trabajo mapResultSetToEntity(ResultSet rs) throws SQLException {
        int idSolicitud = rs.getInt("idSolicitud");
        int idUsuario = rs.getInt("idUsuario");
        Solicitud solicitud = solicitudDAO.findById(idSolicitud);
        UsuarioDeEmpresa usuarioEmpresa = usuarioEmpresaDAO.findById(idUsuario);
        
        return Trabajo.builder()
                .id(rs.getInt("idTrabajo"))
                .fechaHoraInicioActividad(rs.getTimestamp("fechahorainicio") != null ? rs.getTimestamp("fechahorainicio").toLocalDateTime() : null)
                .fechaHoraTerminoActividad(rs.getTimestamp("fechahoratermino") != null ? rs.getTimestamp("fechahoratermino").toLocalDateTime() : null)
                .descripción(rs.getString("descripcion"))
                .solicitud(solicitud)
                .usuario(usuarioEmpresa)
                .build();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tb_trabajo "
                + "(fechahorainicio, fechahoratermino, "
                + "descripcion, idSolicitud, idUsuario) "
                + "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE tb_trabajo SET"
                + "fechahorainicio = ?, "
                + "fechahoratermino = ?, "
                + "descripcion = ? "
                + "idSolicitud = ?, "
                + "idUsuario = ? "
                + "WHERE idTrabajo = ?";
    }

    @Override
    protected void setEntityValues(PreparedStatement ps, Trabajo entidad) throws SQLException {
        ps.setTimestamp(1, entidad.getFechaHoraInicioActividad() != null ? Timestamp.valueOf(entidad.getFechaHoraInicioActividad()) : null);
        ps.setTimestamp(2, entidad.getFechaHoraTerminoActividad() != null ? Timestamp.valueOf(entidad.getFechaHoraTerminoActividad()) : null);
        ps.setString(3, entidad.getDescripción());
        ps.setInt(4, entidad.getSolicitud().getId());
        ps.setInt(5, entidad.getUsuario().getId());
        if (entidad.getId() != 0) {
            ps.setInt(6, entidad.getId());
        }
        
    }

    @Override
    protected String getTableName() {
        return "tb_trabajo";
    }

    @Override
    protected String getIdColumn() {
        return "idTrabajo";
    }
    
}
