package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import modelo.entidad.Solicitud;
import modelo.entidad.UsuarioDeCliente;
import modelo.eums.EstadoSolicitud;
import modelo.eums.TipoSolicitud;

/**
 *
 * @author KEVIN
 */
public class SolicitudDAO extends AbstractDAO<Solicitud>{

    private final UsuarioDeClienteDAO usuarioClienteDAO = new UsuarioDeClienteDAO();
    
    @Override
    protected Solicitud mapResultSetToEntity(ResultSet rs) throws SQLException {
        int idUsuario = rs.getInt("idUsuario");
        UsuarioDeCliente usuarioCliente = usuarioClienteDAO.findById(idUsuario);
        
        return Solicitud.builder()
                .id(rs.getInt("idSolicitud"))
                .year(rs.getInt("anio"))
                .mes(rs.getInt("mes"))
                .numeroCorrelativo(rs.getInt("numCorrelativo"))
                .fechaHora(rs.getTimestamp("fechaHora").toLocalDateTime())
                .fechaHoraAsignacion(rs.getTimestamp("fechaHoraAsignacion") != null ? rs.getTimestamp("fechaHoraAsignacion").toLocalDateTime() : null)
                .fechaHoraInicioAtencion(rs.getTimestamp("fechaHoraInicioAtencion") != null ? rs.getTimestamp("fechaHoraInicioAtencion").toLocalDateTime() : null)
                .fechaHoraTerminoAtencion(rs.getTimestamp("fechaHoraTerminoAtencion") != null ? rs.getTimestamp("fechaHoraTerminoAtencion").toLocalDateTime() : null)
                .motivo(rs.getString("motivo"))
                .estado(EstadoSolicitud.fromCodigo(rs.getString("estado")))
                .tipo(TipoSolicitud.fromCodigo(rs.getString("tipo")))
                .usuario(usuarioCliente)
                .build();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tb_solicitud "
            + "(anio, mes, fechaHoraAsignacion, fechaHoraInicioAtencion, "
            + "fechaHoraTerminoAtencion, motivo, estado, tipo, idUsuario) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE tb_solicitud SET " +
           "anio = ?, " +
           "mes = ?, " +
//           "numCorrelativo = ?, " +
//           "fechaHora = ?, " +
           "fechaHoraAsignacion = ?, " +
           "fechaHoraInicioAtencion = ?, " +
           "fechaHoraTerminoAtencion = ?, " +
           "motivo = ?, " +
           "estado = ?, " +
           "tipo = ?, " +
           "idUsuario = ? " +
           "WHERE idSolicitud = ?";
    }

    @Override
    protected void setEntityValues(PreparedStatement ps, Solicitud entidad) throws SQLException {
        ps.setInt(1, entidad.getYear());
        ps.setInt(2, entidad.getMes());
//        ps.setInt(3, entidad.getNumeroCorrelativo());
//        ps.setTimestamp(1, entidad.getFechaHora() != null ? Timestamp.valueOf(entidad.getFechaHora()) : null);
        ps.setTimestamp(3, entidad.getFechaHoraAsignacion() != null ? Timestamp.valueOf(entidad.getFechaHoraAsignacion()) : null);
        ps.setTimestamp(4, entidad.getFechaHoraInicioAtencion() != null ? Timestamp.valueOf(entidad.getFechaHoraInicioAtencion()) : null);
        ps.setTimestamp(5, entidad.getFechaHoraTerminoAtencion() != null ? Timestamp.valueOf(entidad.getFechaHoraTerminoAtencion()) : null);
        ps.setString(6, entidad.getMotivo());
        ps.setString(7, entidad.getEstado().getCodigo());
        ps.setString(8, entidad.getTipo().getCodigo());
        ps.setInt(9, entidad.getUsuario().getId());
        if (entidad.getId() != 0) {
            ps.setInt(10, entidad.getId());
        }
    }

    @Override
    protected String getTableName() {
        return "tb_solicitud";
    }

    @Override
    protected String getIdColumn() {
        return "idSolicitud";
    }
    
}
