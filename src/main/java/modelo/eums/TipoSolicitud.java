/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package modelo.eums;

/**
 *
 * @author KEVIN
 */
public enum TipoSolicitud {
    ERROR("E", "Error"),
    CAPACITACION("C", "Capacitación"),
    REQUERIMIENTO("R", "Requerimiento");
    
    private final String codigo;
    private final String descripcion;
    
    TipoSolicitud(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Busca un TipoEnum por su código.
     * 
     * @param codigo Código del tipo (E, C, R).
     * @return TipoEnum correspondiente o lanza una excepción si no se encuentra.
     */
    public static TipoSolicitud fromCodigo(String codigo) {
        for (TipoSolicitud tipo : TipoSolicitud.values()) {
            if (tipo.getCodigo().equalsIgnoreCase(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de solicitud no válido: " + codigo);
    }
}
