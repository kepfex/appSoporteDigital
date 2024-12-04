package modelo.facade;

import jakarta.faces.application.FacesMessage;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import modelo.dao.UsuarioDAO;
import modelo.dao.UsuarioDeClienteDAO;
import modelo.dao.UsuarioDeEmpresaDAO;
import modelo.dto.UsuarioDTO;
import modelo.dto.UsuarioLogeadoDTO;
import modelo.entidad.Usuario;
import modelo.entidad.UsuarioDeCliente;
import modelo.entidad.UsuarioDeEmpresa;
import modelo.eums.TipoUsuario;
import modelo.services.Helper;
import modelo.services.MailService;
import modelo.services.PasswordUtils;
import modelo.util.mapper.UsuarioMapper;

/**
 *
 * @author KEVIN
 */
public class UsuarioFacade {

    private final UsuarioDeClienteDAO usuarioDeClienteDAO;
    private final UsuarioDeEmpresaDAO usuarioDeEmpresaDAO;
    private final UsuarioDAO usuarioDAO;

    public UsuarioFacade() {
        this.usuarioDeClienteDAO = new UsuarioDeClienteDAO();
        this.usuarioDeEmpresaDAO = new UsuarioDeEmpresaDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean eliminar(UsuarioDTO usuario) {
        return usuarioDAO.eliminar(usuario.getId());
    }

    public void actualizar(UsuarioDTO usuarioDTO) {
        boolean actualizadoExitosamente;
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(usuarioDTO.getTipo()); // CLIENTE o EMPRESA o SOPORTE

        switch (tipoUsuario) {
            case CLIENTE:
                UsuarioDeCliente usuarioCliente = UsuarioMapper.toUsuarioCliente(usuarioDTO);
                actualizadoExitosamente = usuarioDeClienteDAO.actualizar(usuarioCliente);
                break;
            case EMPRESA:
                UsuarioDeEmpresa usuarioEmpresa = UsuarioMapper.toUsuarioEmpresa(usuarioDTO);
                actualizadoExitosamente = usuarioDeEmpresaDAO.actualizar(usuarioEmpresa);
                break;
            case SOPORTE:
                Usuario usuarioSoporte = UsuarioMapper.toUsuario(usuarioDTO);
                actualizadoExitosamente = usuarioDAO.actualizar(usuarioSoporte);
                break;
            default:
                actualizadoExitosamente = false;
                System.out.println("Tipo de usuario no reconocido.");
                break;
        }

        if (actualizadoExitosamente) {
            // Agrega un mensaje para el growl en la página de destino
            Helper.addFlashMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario actualizado correctamente");

            // Redireccionamos
            switch (tipoUsuario) {
                case CLIENTE:
                    Helper.redirectTo("/usuarios/clientes");
                    break;
                case EMPRESA:
                    Helper.redirectTo("/usuarios/empresa");
                    break;
                case SOPORTE:
                    Helper.redirectTo("/usuarios/soporte");
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }

    public boolean insertar(UsuarioDTO usuarioDTO) {
        boolean creadoExitosamente = false;
        TipoUsuario tipoUsuario = TipoUsuario.valueOf(usuarioDTO.getTipo()); // CLIENTE o EMPRESA o SOPORTE
        String token = UUID.randomUUID().toString(); // Genenar token

        // Inicializamos valores por defecto en el objeto UsuarioDTO
        usuarioDTO.setActivo("ACTIVO");
        usuarioDTO.setConfirmado(false);
        usuarioDTO.setToken(token);

        switch (tipoUsuario) {
            case CLIENTE:
                UsuarioDeCliente usuarioCliente = UsuarioMapper.toUsuarioCliente(usuarioDTO);
                creadoExitosamente = usuarioDeClienteDAO.crear(usuarioCliente);
                break;
            case EMPRESA:
                UsuarioDeEmpresa usuarioEmpresa = UsuarioMapper.toUsuarioEmpresa(usuarioDTO);
                creadoExitosamente = usuarioDeEmpresaDAO.crear(usuarioEmpresa);
                break;
            case SOPORTE:
                Usuario usuarioSoporte = UsuarioMapper.toUsuario(usuarioDTO);
                creadoExitosamente = usuarioDAO.crear(usuarioSoporte);
                break;
            default:
                creadoExitosamente = false;
                System.out.println("Tipo de usuario no reconocido.");
                break;
        }

        if (creadoExitosamente) {
            MailService mailService = new MailService();
            // Enviamos un mensaje al correo registrado
            mailService.enviarConfirmacionDeCuenta(
                    usuarioDTO.getCorreoElectronico(),
                    usuarioDTO.getNombres(),
                    token);
            System.out.println("Correo de creación de contraseña enviado exitosamente.");
        } else {
            System.err.println("Error al insertar el usuario. No se enviará el correo.");
        }

        return creadoExitosamente;
    }

    public List<UsuarioDTO> listarUsuarios(String tipo) {
        if (tipo.equals("C")) {
            List<UsuarioDeCliente> colaboradores = usuarioDeClienteDAO.findAllByColum("tb_usuario_tip", tipo);
            return colaboradores.stream()
                    .map(UsuarioMapper::usuarioCienteToDTO)
                    .collect(Collectors.toList());
        }

        if (tipo.equals("E")) {
            List<UsuarioDeEmpresa> colaboradores = usuarioDeEmpresaDAO.findAllByColum("tb_usuario_tip", tipo);
            return colaboradores.stream()
                    .map(UsuarioMapper::usuarioEmpresaToDTO)
                    .collect(Collectors.toList());
        }
        if (tipo.equals("S")) {
            List<Usuario> colaboradores = usuarioDAO.findAllByColum("tb_usuario_tip", tipo);
            return colaboradores.stream()
                    .map(UsuarioMapper::usuarioToDTO)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public boolean isEmail(String correo) {
        return usuarioDAO.existsByColumn("tb_usuario_corele", correo);
    }

    public UsuarioDTO findUserByEmail(String correo) {
        Usuario usuario = usuarioDAO.findByColum("tb_usuario_corele", correo);
        if (usuario != null) {
            return UsuarioMapper.usuarioToDTO(usuario);
        }
        return null;
    }

    public boolean isToken(String token) {
        return usuarioDAO.existsByColumn("token", token);
    }

    public boolean actualizarPassword(String password, String token) {
        if (isToken(token)) {
            Usuario usuario = usuarioDAO.findByColum("token", token);
            boolean isUpdated = usuarioDAO.updatePassword(usuario.getId(), password);

            if (isUpdated) {
                usuario.setConfirmado(true);
                usuario.setToken(null);
                usuarioDAO.actualizar(usuario);
            }
            return isUpdated;
        }
        return false;
    }

    public UsuarioDTO buscarUsuarioByIdAndTipo(int id, String tipo) {
        switch (tipo) {
            case "C":
                UsuarioDeCliente usuarioDeCliente = usuarioDeClienteDAO.findByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
                return UsuarioMapper.usuarioCienteToDTO(usuarioDeCliente);
            case "E":
                UsuarioDeEmpresa usuarioDeEmpresa = usuarioDeEmpresaDAO.findByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
                ;
                return UsuarioMapper.usuarioEmpresaToDTO(usuarioDeEmpresa);
            case "S":
                Usuario usuarioSoporte = usuarioDAO.findByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
                ;
                return UsuarioMapper.usuarioToDTO(usuarioSoporte);
            default:
                throw new IllegalArgumentException("Tipo de usuario no reconocido.");
        }
    }

    public boolean isUsuarioByIdAndTipo(int id, String tipo) {
        switch (tipo) {
            case "C":
                return usuarioDeClienteDAO.existByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
            case "E":
                return usuarioDeEmpresaDAO.existByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
            case "S":
                return usuarioDAO.existByTwoColumns("tb_usuario_id", id, "tb_usuario_tip", tipo);
            default:
                throw new IllegalArgumentException("Tipo de usuario no reconocido.");
        }
    }

    public List<UsuarioDTO> listarUsuarioPorRol(int idRol, String tipo) {
        switch (tipo) {
            case "C":
                UsuarioDeCliente usuarioDeCliente;
                return null;
            case "E":
                List<UsuarioDeEmpresa> usuariosDeEmpresa = usuarioDeEmpresaDAO.findAllByTwoColums("tb_usuario_tip", tipo, "tb_rol_id", idRol);
                return usuariosDeEmpresa.stream()
                        .map(UsuarioMapper::usuarioEmpresaToDTO)
                        .collect(Collectors.toList());
            case "S":
                Usuario usuarioSoporte;
                return null;
            default:
                throw new IllegalArgumentException("Tipo de usuario no reconocido.");
        }
    }

    public UsuarioLogeadoDTO autenticar(String correo, String passwordIngresada) throws Exception {
        Usuario usuario = usuarioDAO.findByColum("tb_usuario_corele", correo);
        UsuarioLogeadoDTO usuarioLogeadoDTO = null;
        
        if (usuario == null || !usuario.getActivo().isValor()) {
            throw new Exception("Usuario no encontrado o inactivo");
        }
        if (!usuario.isConfirmado()) {
            throw new Exception("Usuario no confirmado. Revisa tu correo electrónico para confirmar tu cuenta.");
        }
        // Verificar contraseña
        boolean esValido = PasswordUtils.verifyPassword(passwordIngresada, usuario.getPassword());
        if (!esValido) {
            throw new Exception("Contraseña incorrecta");
        }

        // URL par Redirigir según el tipo de usuario
        String path = "";
        switch (usuario.getTipo().getCodigo()) {
            case "C":
                path = "/cliente/dashboard";
                break;
            case "E":
                path = "/empresa/dashboard";
                break;
            case "S":
                path = "/soporte/dashboard";
                break;
            default:
                throw new Exception("Tipo de usuario desconocido");
        }
        
        // Construir el DTO de usuario logeado
        usuarioLogeadoDTO = UsuarioLogeadoDTO.builder()
            .idUsuario(usuario.getId())
            .nombres(usuario.getNombres())
            .apellidoPat(usuario.getApellidoPaterno())
            .apellidoMat(usuario.getApellidoMaterno())
            .correo(usuario.getCorreoElectronico())
            .tipoUsuario(usuario.getTipo())
            .PathURL(path)
            .build();
        return usuarioLogeadoDTO;
    }
}
