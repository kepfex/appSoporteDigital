package controlador.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import modelo.dto.ClienteDTO;
import modelo.dto.RolDTO;
import modelo.dto.UsuarioDTO;
import modelo.entidad.Cliente;
import modelo.entidad.Rol;
import modelo.eums.TipoUsuario;
import modelo.facade.ClienteFacade;
import modelo.facade.RolFacade;
import modelo.facade.UsuarioFacade;
import modelo.services.Helper;
import org.primefaces.PrimeFaces;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class PruebasBean implements Serializable {

    private boolean mostrarDialogo = false;
    private TipoUsuario tipoUsuario;
    private UsuarioDTO usuarioDTO;
    private List<UsuarioDTO> usuariosDTO;
    private UsuarioFacade usuarioFacade;
    private ClienteFacade clienteFacade;
    private RolFacade rolFacde;

    @PostConstruct
    public void init() {
        usuarioFacade = new UsuarioFacade();
        clienteFacade = new ClienteFacade();
        rolFacde = new RolFacade();

        // Detectar el tipo de usuario desde la URL
        tipoUsuario = detectarTipoUsuario();
        openNew(); // Instanciamos las entidades
        this.usuarioDTO.setTipo(tipoUsuario.name());
        this.usuariosDTO = usuarioFacade.listarUsuarios(tipoUsuario.getCodigo());
    }

    public void openNew() {
        this.usuarioDTO = new UsuarioDTO();
        if (this.usuarioDTO.getCliente() == null) {
            this.usuarioDTO.setCliente(new Cliente());
        }
        if (this.usuarioDTO.getRol() == null) {
            this.usuarioDTO.setRol(new Rol());
        }
    }

    public void guardarUsuarioDelCliente() {
        if (usuarioFacade.isEmail(usuarioDTO.getCorreoElectronico())) {
            FacesMessage msgCorreoExiste = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "El correo '" + usuarioDTO.getCorreoElectronico() + "' ya se encuentra registrado"
            );
            FacesContext.getCurrentInstance().addMessage(null, msgCorreoExiste);
//            PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
            return;
        }

        // Insertamos a la base de datos
        if (this.usuarioDTO.getId() == 0) {
            boolean insertadoExitosamente = usuarioFacade.insertar(usuarioDTO);

            if (insertadoExitosamente) {
                // Activa el diálogo de confirmación
                mostrarDialogo = insertadoExitosamente;
            }
        }
    }

    public void eliminarUsuario() {
        if (usuarioFacade.eliminar(this.usuarioDTO)) {
            // Eliminamos al usuario manualmente de la lista
            usuariosDTO.removeIf(u->u.getId() == usuarioDTO.getId());

            // Limpiamos el usuario seleccionado
            openNew();
            
            Helper.addContextMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario eliminado correctamente");
            // Actualizamos la tabla y los mensajes
            PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } else {
            Helper.addContextMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede eliminar al usuario");
        }
    }

    private TipoUsuario detectarTipoUsuario() {
        String url = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestServletPath();

        String pathListarCliente = "/faces/layouts/usuarios/clientes/usuarios-cliente.xhtml";
        String pathCrearCliente = "/faces/layouts/usuarios/clientes/cliente-crear.xhtml";
        String pathListarEmpresa = "/faces/layouts/usuarios/empresa/listar.xhtml";
        String pathCrearEmpresa = "/faces/layouts/usuarios/empresa/crear.xhtml";
        String pathListarSoporte = "/faces/layouts/usuarios/soporte/listar.xhtml";
        String pathCrearSoporte = "/faces/layouts/usuarios/soporte/crear.xhtml";

        // Verificar el segmento de la URL
        if (url.contains(pathListarCliente) || url.contains(pathCrearCliente)) {
            return TipoUsuario.CLIENTE;
        } else if (url.contains(pathCrearEmpresa) || url.contains(pathListarEmpresa)) {
            return TipoUsuario.EMPRESA;
        } else if (url.contains(pathListarSoporte) || url.contains(pathCrearSoporte)) {
            return TipoUsuario.SOPORTE;
        } else {
            throw new IllegalArgumentException("URL no válida para detectar el tipo de usuario: " + url);
        }
    }

    public void aceptarDialogo() {
        // Agrega un mensaje para el growl en la página de destino
        Helper.addFlashMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario registrado correctamente");

        // Redirige a /usuarios del cliente
        switch (tipoUsuario.getCodigo()) {
            case "C":
                Helper.redirectTo("/usuarios/clientes");
                break;
            case "E":
                Helper.redirectTo("/usuarios/empresa");
                break;
            case "S":
                Helper.redirectTo("/usuarios/soporte");
                break;
            default:
                throw new AssertionError();
        }
    }

    // Listar Razon Social de Clientes
    public List<ClienteDTO> getListaClientesForSelect() {
        return clienteFacade.listarClientes();
    }

    // Litar Roles para colaborades de empresa
    public List<RolDTO> getListaRoles() {
        return rolFacde.listarRoles();
    }
}
