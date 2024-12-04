package controlador.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class HelpersBean implements Serializable {

    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("hh:mm a");

}
