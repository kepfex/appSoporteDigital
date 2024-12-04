package modelo.eums;

import java.io.Serializable;

/**
 *
 * @author KEVIN
 */
public enum TipoUsuario implements Serializable {
    CLIENTE("C"),
    EMPRESA("E"),
    SOPORTE("S");

    private final String codigo;

    private TipoUsuario(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoUsuario fromCodigo(String codigo) {
        for (TipoUsuario tipo : TipoUsuario.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de usuario no válido: " + codigo);
    }

    @Override
    public String toString() {
        return this.codigo;
    }
}
