package modelo.eums;

/**
 *
 * @author KEVIN
 */
public enum TipoDocumentoIdentidad {
    DTNDSR("0", "DTNDSR - Documento Tributario de No Domiciliado sin RUC"),
    DNI("1", "DNI - Documento Nacional de Identidad"),
    CE("4", "CE - Carnet de Extranjería"),
    RUC("6", "RUC - Registro Único de Contribuyentes"),
    PAS("7", "PAS - Pasaporte"),
    CDI("A", "CDI - Cédula Diplomática de Identidad"),
    DIPRND("B", "DIPRND - Doc.Ident.País.Residencia-NO.D."),
    TIN("C", "TIN - Tax Identification Number - TIN – Doc Trib PP.NN"),
    IN("D", "IN - Identification Number - IN – Doc Trib PP. JJ"),
    TAM("E", "TAM - Tarjeta Andina de Migración");

    private final String codigo;
    private final String descripcion;

    // Constructor del enum
    private TipoDocumentoIdentidad(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Método estático para buscar el enum por código
    public static TipoDocumentoIdentidad obtenerPorCodigo(String codigo) {
        for (TipoDocumentoIdentidad tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo de documento no válido: " + codigo);
    }

    @Override
    public String toString() {
        return this.codigo;
    }
}
