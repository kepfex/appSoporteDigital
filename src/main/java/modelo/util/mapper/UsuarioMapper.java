package modelo.util.mapper;

import modelo.dto.UsuarioDTO;
import modelo.entidad.UsuarioDeCliente;
import modelo.entidad.UsuarioDeEmpresa;
import modelo.entidad.Usuario;
import modelo.eums.EstadoEntidad;
import modelo.eums.TipoDocumentoIdentidad;
import modelo.eums.TipoUsuario;

/**
 *
 * @author KEVIN
 */
public class UsuarioMapper {
    
    public static UsuarioDTO usuarioCienteToDTO(UsuarioDeCliente colaborador) {
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(colaborador.getId())
                .tipoDeDocumentoDeIdentidad(colaborador.getTipoDeDocumentoDeIdentidad().name())
                .numeroDeDocumentoDeIdentidad(colaborador.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(colaborador.getApellidoPaterno())
                .apellidoMaterno(colaborador.getApellidoMaterno())
                .nombres(colaborador.getNombres())
                .correoElectronico(colaborador.getCorreoElectronico())
                .activo(colaborador.getActivo().name())
                .tipo(colaborador.getTipo().name())
                .cliente(colaborador.getCliente())
                .rol(null)
                .confirmado(colaborador.isConfirmado())
                .token(colaborador.getToken())
                .build();
        return usuarioDTO;
    }
    
    public static UsuarioDeCliente toUsuarioCliente(UsuarioDTO usuarioDTO) {
        UsuarioDeCliente colaborador = UsuarioDeCliente.builder()
                .id(usuarioDTO.getId())
                .tipoDeDocumentoDeIdentidad(TipoDocumentoIdentidad.valueOf(usuarioDTO.getTipoDeDocumentoDeIdentidad()))
                .numeroDeDocumentoDeIdentidad(usuarioDTO.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(usuarioDTO.getApellidoPaterno())
                .apellidoMaterno(usuarioDTO.getApellidoMaterno())
                .nombres(usuarioDTO.getNombres())
                .correoElectronico(usuarioDTO.getCorreoElectronico())
                .activo(EstadoEntidad.valueOf(usuarioDTO.getActivo()))
                .tipo(TipoUsuario.valueOf(usuarioDTO.getTipo()))
                .confirmado(usuarioDTO.isConfirmado())
                .token(usuarioDTO.getToken())
                .cliente(usuarioDTO.getCliente())
                .build();
        return colaborador;
    }
    
    public static UsuarioDTO usuarioEmpresaToDTO(UsuarioDeEmpresa colaborador) {
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(colaborador.getId())
                .tipoDeDocumentoDeIdentidad(colaborador.getTipoDeDocumentoDeIdentidad().name())
                .numeroDeDocumentoDeIdentidad(colaborador.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(colaborador.getApellidoPaterno())
                .apellidoMaterno(colaborador.getApellidoMaterno())
                .nombres(colaborador.getNombres())
                .correoElectronico(colaborador.getCorreoElectronico())
                .activo(colaborador.getActivo().name())
                .tipo(colaborador.getTipo().name())
                .cliente(null)
                .rol(colaborador.getRol())
                .confirmado(colaborador.isConfirmado())
                .token(colaborador.getToken())
                .build();
        return usuarioDTO;
    }
    
    public static UsuarioDeEmpresa toUsuarioEmpresa(UsuarioDTO usuarioDTO) {
        UsuarioDeEmpresa colaborador = UsuarioDeEmpresa.builder()
                .id(usuarioDTO.getId())
                .tipoDeDocumentoDeIdentidad(TipoDocumentoIdentidad.valueOf(usuarioDTO.getTipoDeDocumentoDeIdentidad()))
                .numeroDeDocumentoDeIdentidad(usuarioDTO.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(usuarioDTO.getApellidoPaterno())
                .apellidoMaterno(usuarioDTO.getApellidoMaterno())
                .nombres(usuarioDTO.getNombres())
                .correoElectronico(usuarioDTO.getCorreoElectronico())
                .activo(EstadoEntidad.valueOf(usuarioDTO.getActivo()))
                .tipo(TipoUsuario.valueOf(usuarioDTO.getTipo()))
                .confirmado(usuarioDTO.isConfirmado())
                .token(usuarioDTO.getToken())
                .rol(usuarioDTO.getRol())
                .build();
        return colaborador;
    }
    
    public static UsuarioDTO usuarioToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(usuario.getId())
                .tipoDeDocumentoDeIdentidad(usuario.getTipoDeDocumentoDeIdentidad().name())
                .numeroDeDocumentoDeIdentidad(usuario.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(usuario.getApellidoPaterno())
                .apellidoMaterno(usuario.getApellidoMaterno())
                .nombres(usuario.getNombres())
                .correoElectronico(usuario.getCorreoElectronico())
                .activo(usuario.getActivo().name())
                .tipo(usuario.getTipo().name())
                .cliente(null)
                .confirmado(usuario.isConfirmado())
                .token(usuario.getToken())
                .build();
        return usuarioDTO;
    }
    
    public static Usuario toUsuario (UsuarioDTO usuarioDTO) {
        Usuario colaborador = Usuario.builder()
                .id(usuarioDTO.getId())
                .tipoDeDocumentoDeIdentidad(TipoDocumentoIdentidad.valueOf(usuarioDTO.getTipoDeDocumentoDeIdentidad()))
                .numeroDeDocumentoDeIdentidad(usuarioDTO.getNumeroDeDocumentoDeIdentidad())
                .apellidoPaterno(usuarioDTO.getApellidoPaterno())
                .apellidoMaterno(usuarioDTO.getApellidoMaterno())
                .nombres(usuarioDTO.getNombres())
                .correoElectronico(usuarioDTO.getCorreoElectronico())
                .activo(EstadoEntidad.valueOf(usuarioDTO.getActivo()))
                .tipo(TipoUsuario.valueOf(usuarioDTO.getTipo()))
                .confirmado(usuarioDTO.isConfirmado())
                .token(usuarioDTO.getToken())
                .build();
        return colaborador;
    }
}
