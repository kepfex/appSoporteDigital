package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.entidad.Rol;

/**
 *
 * @author KEVIN
 */
public class RolDAO extends AbstractDAO<Rol>{
    
    @Override
    protected Rol mapResultSetToEntity(ResultSet rs) throws SQLException {
        return Rol.builder()
                .id(rs.getInt("tb_rol_id"))
                .nombre(rs.getString("tb_rol_nom"))
                .build();
    }

    @Override
    protected String getInsertQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setEntityValues(PreparedStatement ps, Rol entidad) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getTableName() {
        return "tb_rol";
    }

    @Override
    protected String getIdColumn() {
        return "tb_rol_id";
    }
}
