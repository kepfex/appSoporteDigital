package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KEVIN
 * @param <T>
 */
public abstract class AbstractDAO<T> implements ICrud<T> {

    protected Connection connection;

    public AbstractDAO() {
        try {
            this.connection = ConexionDB.getInstance().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract void setEntityValues(PreparedStatement ps, T entidad) throws SQLException;

    protected abstract String getTableName();

    protected abstract String getIdColumn();

    @Override
    public boolean crear(T entidad) {
        try (PreparedStatement ps = connection.prepareStatement(getInsertQuery())) {
            setEntityValues(ps, entidad);
            int filaAfectada = ps.executeUpdate();
            return filaAfectada > 0;// Retorna true si se afectó al menos una fila
        } catch (SQLException ex) {
            System.err.println("Hubo un error al crear: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Indica fallo
        }
    }

    @Override
    public T findById(Object entidadId) {
        T entidad = null;
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entidadId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entidad = mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Hubo un error al buscar por id: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entidad;
    }

    @Override
    public boolean actualizar(T entidad) {
        try (PreparedStatement ps = connection.prepareStatement(getUpdateQuery())) {
            setEntityValues(ps, entidad);
            int filaAfectada = ps.executeUpdate();
            return filaAfectada > 0;// Retorna true si se afectó al menos una fila
        } catch (SQLException ex) {
            System.err.println("Hubo un error al actualizar: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Indica fallo
        }
    }

    @Override
    public boolean eliminar(Object entidadId) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entidadId);
            int filasAfectada = ps.executeUpdate();
            return filasAfectada > 0; // Si se afectó al menos una fila, retorna true
        } catch (SQLException ex) {
            System.err.println("Hubo un error al elminar : " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Error al eliminar: " + ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Hubo un error al buscar todos: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return entities;
    }

    public List<T> findAllByColum(String columna, Object valor) {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + columna + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Hubo un error al buscar todos por columna: " + ex.getMessage(), ex);
        }
        return entities;
    }
    
    public T findByColum(String columna, Object valor) {
        T entidad = null;
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + columna + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entidad = mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Hubo un error al buscar por columna: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entidad;
    }

    // Método genérico para verificar si existe un registro en cualquier columna
    public boolean existsByColumn(String columna, Object valor) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + columna + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si hay al menos un resultado
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Error al buscar registro: " + ex.getMessage(), ex);
        }
        return false;
    }

    public boolean updateCampo(String columnName, Object newValue, String idColumn, Object idValue) {
        String sql = "UPDATE " + getTableName() + " SET " + columnName + " = ? WHERE " + idColumn + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, newValue);
            ps.setObject(2, idValue);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Error en actualizarCampo: " + ex.getMessage(), ex);
            return false;
        }
    }
    
    public boolean updateByTwoCampo(String columnName, Object newValue, String idColumn1, Object idValue1, String idColumn2, Object idValue2) {
        String sql = "UPDATE " + getTableName() + " SET " + columnName + " = ? WHERE " + idColumn1 + " = ? AND " + idColumn2 + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, newValue);
            ps.setObject(2, idValue1);
            ps.setObject(3, idValue2);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Error en actualizarCampo: " + ex.getMessage(), ex);
            return false;
        }
    }
    
    public T findByTwoColumns(String columna1, Object valor1, String columna2, Object valor2) {
        T entidad = null;
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + columna1 + " = ? AND " + columna2 + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor1);
            ps.setObject(2, valor2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entidad = mapResultSetToEntity(rs);
            }
        } catch (SQLException ex) {
            System.err.println("Hubo un error al listar por dos columnas: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entidad;
    }
    
    public boolean existByTwoColumns(String columna1, Object valor1, String columna2, Object valor2) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + columna1 + " = ? AND " + columna2 + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor1);
            ps.setObject(2, valor2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si hay al menos un resultado
            }
        } catch (SQLException ex) {
            System.err.println("Hubo un error al buscar por dos columnas: " + ex.getMessage());
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<T> findAllByTwoColums(String columna1, Object valor1, String columna2, Object valor2) {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + columna1 + " = ? AND " + columna2 + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, valor1);
            ps.setObject(2, valor2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, "Hubo un error al buscar todos por columna: " + ex.getMessage(), ex);
        }
        return entities;
    }
}
