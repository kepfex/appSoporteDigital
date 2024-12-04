package modelo.services;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 *
 * @author KEVIN
 */
public class MailService {

    private final String host = "smtp.gmail.com";
    private final String port = "587";
    private final String username = "app.dev.kep@gmail.com"; // Correo desde el que enviarás
    private final String password = "zswntqsrsipizmxa"; // Reemplaza con tu contraseña o contraseña de aplicación

    private final String localhost = "http://localhost:8080/appSoporteDigital/";

    private Session configureMailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            Session session = configureMailSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Soporte Digital"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Correo enviado a " + toEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public void enviarConfirmacionDeCuenta(String toEmail, String name, String token) {
        String subject = "Confirma tu Cuenta";
        String link = localhost + "confirmar-cuenta/" + token;
        String body = "<html><p><strong>Hola " + name + "</strong>, Se ha registrado correctamente tu cuenta en Soporte Digital, pero es necesario confirmarla.</p>"
                + "<p>Presiona aquí: <a href='" + link + "'>Confirmar Cuenta</a></p>"
                + "<p>Si tú no creaste esta cuenta, puedes ignorar el mensaje.</p></html>";

        sendEmail(toEmail, subject, body);
    }

    public void enviarNotificacionAtencion(String toEmail, String name, String razonSocial, String codigoSolicitud, String fechaFinalizacion) {
        String subject = "Solicitud Atendida";
        String body = "<html>"
                + "<p><strong>Hola " + name + "</strong>,</p>"
                + "<p>Nos complace informarte que la solicitud del cliente <strong>" + razonSocial + "</strong> con el código <strong>" + codigoSolicitud + "</strong> ha sido atendida.</p>"
                + "<p>Fecha de finalización: <strong>" + fechaFinalizacion + "</strong>.</p>"
                + "<p>Gracias por confiar en nuestro servicio. Si tienes alguna pregunta o necesitas más información, no dudes en contactarnos.</p>"
                + "<p>Atentamente,<br>El equipo de Soporte Digital</p>"
                + "</html>";

        sendEmail(toEmail, subject, body);
    }
}
