package modelo.dao;

import java.util.List;

/**
 *
 * @author KEVIN
 * @param <T>
 */
public interface ICrud<T> {
    boolean crear(T entidad);
    T findById(Object entidadId);
    boolean actualizar(T entidad);
    boolean eliminar(Object entidadId);
    List<T> findAll();
}
