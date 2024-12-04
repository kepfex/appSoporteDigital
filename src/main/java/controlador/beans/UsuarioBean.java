package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.ClienteDTO;
import modelo.dto.UsuarioDTO;
import modelo.entidad.Cliente;
import modelo.eums.TipoDocumentoIdentidad;
import modelo.facade.ClienteFacade;
import modelo.facade.RolFacade;
import modelo.facade.UsuarioFacade;
import modelo.services.MailService;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped
@Getter
@Setter
public class UsuarioBean implements Serializable {

    private UsuarioFacade usuarioFacade;
    private ClienteFacade clienteFacade;
    private UsuarioDTO usuarioSeleccionado;
    private RolFacade rolFacade;

    private String tipoDocumento;
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String correo;
    private String password;
    private Boolean estado = true;
    private Integer idCliente = null;

    private boolean mostrarDialogo = false; // Controla la visibilidad del diálogo

    @PostConstruct
    public void init() {
        usuarioFacade = new UsuarioFacade();
        clienteFacade = new ClienteFacade();
        rolFacade = new RolFacade();
    }

    /**
     * @return Carga la lista de colaboradores de cliente desde el facade.
     */
//    public List<UsuarioDTO> getListaColaboradores() {
//        return usuarioFacade.listarColaboradores();
//    }

    public List<ClienteDTO> getListaClientesForSelect() {
        return clienteFacade.listarClientes();
    }

    public void nuevo() {
        usuarioSeleccionado = new UsuarioDTO();
    }

    public void guardar() {
        if (usuarioFacade.isEmail(correo)) {
            FacesMessage msgCorreoExiste = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "El correo '" + correo + "' ya se encuentra registrado"
            );
            FacesContext.getCurrentInstance().addMessage(null, msgCorreoExiste);
//            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            return;
        }
        

        // Si pasa las validaciones contruimos el UsuarioDTO
        usuarioSeleccionado = UsuarioDTO.builder()
                .tipoDeDocumentoDeIdentidad(tipoDocumento)
                .numeroDeDocumentoDeIdentidad(numeroDocumento)
                .apellidoPaterno(apellidoPaterno)
                .apellidoMaterno(apellidoMaterno)
                .nombres(nombres)
                .correoElectronico(correo)
//                .activo(estado ? "ACTIVO" : "INACTIVO")
                .cliente(new Cliente(idCliente))
                .build();

        // Insertamos a la base de datos
        usuarioFacade.insertar(usuarioSeleccionado);

        // Activa el diálogo de confirmación
        mostrarDialogo = true;
    }

    public void aceptarDialogo() {
        // Agrega un mensaje para el growl en la página de destino
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario registrado correctamente"));

        // Redirige a /usuarios
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/usuarios/clientes");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public TipoDocumentoIdentidad[] getTiposDocumentos() {
        return TipoDocumentoIdentidad.values();
    }

    public void submitPrueba() {
        FacesMessage msg;
        if (tipoDocumento != null) {
            msg = new FacesMessage(tipoDocumento);
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalido", "Seleccione un tipo de documento");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.println("Tipo de documento seleccionado id: " + tipoDocumento);

    }

    public void enviarCorreo() {
        MailService mailService = new MailService();
        // Generar un token único para el enlace de creación de contraseña
        String token = UUID.randomUUID().toString();
        System.out.println(token);
        // Guarda el token en tu base de datos asociado al usuario para luego validarlo cuando acceda al enlace
        mailService.enviarConfirmacionDeCuenta("kevinlepoma@gmail.com", "Kevin", token);
        System.out.println("Correo de creación de contraseña enviado exitosamente.");
    }
}
