package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import modelo.entidad.Asignacion;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeEmpresa;

/**
 *
 * @author KEVIN
 */
public class AsignacionDAO extends AbstractDAO<Asignacion> {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final UsuarioDeEmpresaDAO usuarioEmpresaDAO = new UsuarioDeEmpresaDAO();

    @Override
    protected Asignacion mapResultSetToEntity(ResultSet rs) throws SQLException {
        int idSolicitud = rs.getInt("idSolicitud");
        int idUsuario = rs.getInt("idUsuario");
        Solicitud solicitud = solicitudDAO.findById(idSolicitud);
        UsuarioDeEmpresa usuarioEmpresa = usuarioEmpresaDAO.findById(idUsuario);

        return Asignacion.builder()
                .fechaHoraAsignacion(rs.getTimestamp("fechaHora") != null ? rs.getTimestamp("fechaHora").toLocalDateTime() : null)
                .fechaHoraInicioAtencion(rs.getTimestamp("fechaHoraInicioAtencion") != null ? rs.getTimestamp("fechaHoraInicioAtencion").toLocalDateTime() : null)
                .fechaHoraTerminoAtencion(rs.getTimestamp("fechaHoraTerminoAtencion") != null ? rs.getTimestamp("fechaHoraTerminoAtencion").toLocalDateTime() : null)
                .isCoordinador(rs.getBoolean("coordinador"))
                .solicitud(solicitud)
                .usuario(usuarioEmpresa)
                .build();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tb_asignacion "
                + "(fechaHoraInicioAtencion, fechaHoraTerminoAtencion, "
                + "coordinador, idSolicitud, idUsuario) "
                + "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE tb_asignacion SET"
                + "fechaHoraInicioAtencion = ?, "
                + "fechaHoraTerminoAtencion = ?, "
                + "coordinador = ? "
                + "WHERE idSolicitud = ? AND idUsuario = ? ";
    }

    @Override
    protected void setEntityValues(PreparedStatement ps, Asignacion entidad) throws SQLException {
        ps.setTimestamp(1, entidad.getFechaHoraInicioAtencion() != null ? Timestamp.valueOf(entidad.getFechaHoraInicioAtencion()) : null);
        ps.setTimestamp(2, entidad.getFechaHoraTerminoAtencion() != null ? Timestamp.valueOf(entidad.getFechaHoraTerminoAtencion()) : null);
        ps.setBoolean(3, entidad.isCoordinador());
        ps.setInt(4, entidad.getSolicitud().getId());
        ps.setInt(5, entidad.getUsuario().getId());
    }

    @Override
    protected String getTableName() {
        return "tb_asignacion";
    }

    @Override
    protected String getIdColumn() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
