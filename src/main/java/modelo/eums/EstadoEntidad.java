package modelo.eums;

/**
 *
 * @author KEVIN
 */
public enum EstadoEntidad {
    ACTIVO(true),
    INACTIVO(false);

    private final boolean valor;

    private EstadoEntidad(boolean valor) {
        this.valor = valor;
    }

    public boolean isValor() {
        return valor;
    }

    public static EstadoEntidad fromBoolean(boolean valor) {
        return valor ? ACTIVO : INACTIVO;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
