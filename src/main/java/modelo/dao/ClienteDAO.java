package modelo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.entidad.Cliente;

/**
 *
 * @author KEVIN
 */
public class ClienteDAO extends AbstractDAO<Cliente>{

    @Override
    protected Cliente mapResultSetToEntity(ResultSet rs) throws SQLException {
        // Construye un Cliente a partir de los datos del ResultSet
        
        return Cliente.builder()
                .id(rs.getInt("tb_cliente_id"))
                .tipoDeDocumentoDeIdentidad(rs.getString("tb_cliente_tipdocide"))
                .numeroDeDocumentoDeIdentidad(rs.getString("tb_cliente_numdocide"))
                .razonSocial(rs.getString("tb_cliente_razsoc"))
                .direccion(rs.getString("tb_cliente_dir"))
                .telefono(rs.getString("tb_cliente_tel"))
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
    protected void setEntityValues(PreparedStatement ps, Cliente entidad) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getTableName() {
        return "tb_cliente";
    }

    @Override
    protected String getIdColumn() {
        return "tb_cliente_id";
    }
    
}
