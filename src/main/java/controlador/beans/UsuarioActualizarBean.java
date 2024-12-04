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
import modelo.eums.TipoUsuario;
import modelo.facade.ClienteFacade;
import modelo.facade.RolFacade;
import modelo.facade.UsuarioFacade;
import modelo.services.Helper;

/**
 *
 * @author KEVIN
 */
@Named
@ViewScoped

@Getter
@Setter
public class UsuarioActualizarBean implements Serializable {

    private UsuarioFacade usuarioClienteFacade;
    private UsuarioDTO usuarioDTO;
    private Integer idUsuario;
    private Boolean mostrarFormulario;
    private ClienteFacade clienteFacade;
    private RolFacade rolFacde;
    private TipoUsuario tipoUsuario;

    @PostConstruct
    public void init() {
        usuarioClienteFacade = new UsuarioFacade();
        clienteFacade = new ClienteFacade();
        rolFacde = new RolFacade();
        String idUsuarioURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUsuario");

        try {
            if (idUsuarioURL != null && idUsuarioURL.matches("\\d+")) { // Solo números positivos
                idUsuario = Integer.valueOf(idUsuarioURL);
                // Detectar el tipo de usuario desde la URL
                tipoUsuario = detectarTipoUsuario();
                boolean existeUsuario = usuarioClienteFacade.isUsuarioByIdAndTipo(idUsuario, tipoUsuario.getCodigo());

                if (!existeUsuario) {
                    mostrarFormulario = existeUsuario;
                    return;
                }

                usuarioDTO = usuarioClienteFacade.buscarUsuarioByIdAndTipo(idUsuario, tipoUsuario.getCodigo());
                mostrarFormulario = existeUsuario;

            } else {
                mostrarFormulario = false;
                System.out.println("Id del usuario no recibido o no válido.");
            }
        } catch (Exception e) {
            mostrarFormulario = false;
            System.out.println("Error procesando el ID del usuario: " + e.getMessage());
        }

    }

    public void actualizarUsuarioCliente() {
        UsuarioDTO existeUsuario = usuarioClienteFacade.findUserByEmail(usuarioDTO.getCorreoElectronico());

        if (existeUsuario != null && existeUsuario.getId() != usuarioDTO.getId()) {
            Helper.addContextMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo Electrónico no válido, cuenta ya registrada.");
        } else {
            usuarioClienteFacade.actualizar(usuarioDTO);
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

    private TipoUsuario detectarTipoUsuario() {
        String url = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestServletPath();

        System.out.println(url);
        String pathActualizarCliente = "/faces/layouts/usuarios/clientes/cliente-actualizar.xhtml";
        String pathActualizarEmpresa = "/faces/layouts/usuarios/empresa/actualizar.xhtml";
        String pathActualizarSoporte = "/faces/layouts/usuarios/soporte/actualizar.xhtml";

        // Verificar el segmento de la URL
        if (url.contains(pathActualizarCliente)) {
            return TipoUsuario.CLIENTE;
        } else if (url.contains(pathActualizarEmpresa)) {
            return TipoUsuario.EMPRESA;
        } else if (url.contains(pathActualizarSoporte)) {
            return TipoUsuario.SOPORTE;
        } else {
            throw new IllegalArgumentException("URL no válida para detectar el tipo de usuario: " + url);
        }
    }
}
