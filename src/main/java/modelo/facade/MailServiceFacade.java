package modelo.facade;

import controlador.beans.HelpersBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.dto.SolicitudDTO;
import modelo.services.MailService;

/**
 *
 * @author KEVIN
 */
public class MailServiceFacade {
    MailService mailService;
    
    public void enviarEmailFinalizacionSolicitud(SolicitudDTO solicitud, LocalDateTime fechaHoraActual) {
        this.mailService = new MailService();
        String codigoSolicitud = ""+solicitud.getYear()+solicitud.getMes()+solicitud.getNumeroCorrelativo();
        String toEmail = solicitud.getUsuario().getCorreoElectronico();
        String name = solicitud.getUsuario().getNombres();
        String razonSocial = solicitud.getUsuario().getCliente().getRazonSocial();
        String fechaHoraFin = ""+fechaHoraActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" - "+
                                 fechaHoraActual.format(DateTimeFormatter.ofPattern("hh:mm a"));
        mailService.enviarNotificacionAtencion(toEmail, name, razonSocial, codigoSolicitud, fechaHoraFin);
    }
}
