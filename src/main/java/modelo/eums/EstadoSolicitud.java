package modelo.eums;

/**
 *
 * @author KEVIN
 */
public enum EstadoSolicitud {
    PENDIENTE("P", "Pendiente"),
    ASIGNADA("A", "Asignada"),
    EN_PROCESO("R", "En Proceso"),
    ATENDIDA("T", "Atendida");

    private final String codigo;
    private final String descripcion;

    EstadoSolicitud(String codigo, String descripcion) {
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
     * Busca un EstadoEnum por su código.
     * 
     * @param codigo Código del estado (P, A, R, T).
     * @return EstadoEnum correspondiente o lanza una excepción si no se encuentra.
     */
    public static EstadoSolicitud fromCodigo(String codigo) {
        for (EstadoSolicitud estado : EstadoSolicitud.values()) {
            if (estado.getCodigo().equalsIgnoreCase(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de solicitud no válido: " + codigo);
    }
}
