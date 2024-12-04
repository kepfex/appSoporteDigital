package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.entidad.UsuarioDeEmpresa;
import modelo.entidad.Usuario;
import modelo.eums.EstadoEntidad;
import modelo.eums.TipoDocumentoIdentidad;
import modelo.eums.TipoUsuario;

/**
 *
 * @author KEVIN
 */
public class UsuarioDAO extends AbstractDAO<Usuario>{

    @Override
    protected Usuario mapResultSetToEntity(ResultSet rs) throws SQLException {
        // Construye un ColaboradorDeEmpresa a partir de los datos del ResultSet
        return UsuarioDeEmpresa.builder()
                .id(rs.getInt("tb_usuario_id"))
                .tipoDeDocumentoDeIdentidad(TipoDocumentoIdentidad.obtenerPorCodigo(rs.getString("tb_usuario_tipdocide")))
                .numeroDeDocumentoDeIdentidad(rs.getString("tb_usuario_numdocide"))
                .apellidoPaterno(rs.getString("tb_usuario_apepat"))
                .apellidoMaterno(rs.getString("tb_usuario_apemat"))
                .nombres(rs.getString("tb_usuario_nom"))
                .correoElectronico(rs.getString("tb_usuario_corele"))
                .password(rs.getString("tb_usuario_con"))
                .activo(EstadoEntidad.fromBoolean(rs.getBoolean("tb_usuario_act")))
                .tipo(TipoUsuario.fromCodigo(rs.getString("tb_usuario_tip")))
                .confirmado(rs.getBoolean("confirmado"))
                .token(rs.getString("token"))
                .build();
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO tb_usuario "
                + "(tb_usuario_tipdocide, tb_usuario_numdocide, tb_usuario_apepat, tb_usuario_apemat, tb_usuario_nom, "
                + "tb_usuario_corele, tb_usuario_act, tb_usuario_tip, confirmado, token) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE tb_usuario " +
             "SET tb_usuario_tipdocide = ?, " +
             "tb_usuario_numdocide = ?, " +
             "tb_usuario_apepat = ?, " +
             "tb_usuario_apemat = ?, " +
             "tb_usuario_nom = ?, " +
             "tb_usuario_corele = ?, " +
             "tb_usuario_act = ?, " +
             "tb_usuario_tip = ?, " +
             "confirmado = ?, " +
             "token = ? " +
             "WHERE tb_usuario_id = ?";
    }

    @Override
    protected void setEntityValues(PreparedStatement ps, Usuario entidad) throws SQLException {
        ps.setString(1, entidad.getTipoDeDocumentoDeIdentidad().getCodigo());
        ps.setString(2, entidad.getNumeroDeDocumentoDeIdentidad());
        ps.setString(3, entidad.getApellidoPaterno());
        ps.setString(4, entidad.getApellidoMaterno());
        ps.setString(5, entidad.getNombres());
        ps.setString(6, entidad.getCorreoElectronico());
        ps.setBoolean(7, entidad.getActivo().isValor());
        ps.setString(8, entidad.getTipo().getCodigo());
        ps.setBoolean(9, entidad.isConfirmado());
        ps.setString(10, entidad.getToken());
        if (entidad.getId() != 0) {
            ps.setInt(11, entidad.getId());
        }
    }

    @Override
    protected String getTableName() {
        return "tb_usuario";
    }

    @Override
    protected String getIdColumn() {
        return "tb_usuario_id";
    }
    
    public boolean updatePassword(int id, String newPassword){
        return updateCampo("tb_usuario_con", newPassword, "tb_usuario_id", id);
    }
}