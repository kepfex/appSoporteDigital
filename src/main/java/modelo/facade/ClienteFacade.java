package modelo.facade;

import java.util.List;
import java.util.stream.Collectors;
import modelo.dao.ClienteDAO;
import modelo.dto.ClienteDTO;
import modelo.entidad.Cliente;

/**
 *
 * @author KEVIN
 */
public class ClienteFacade {
    private final ClienteDAO clienteDAO;
    
    public ClienteFacade() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteDAO.findAll();
        
        return clientes.stream()
                .map(this::convertirAClienteDTO)
                .collect(Collectors.toList());
    }
    
    private ClienteDTO convertirAClienteDTO(Cliente cliente){
        ClienteDTO clienteDTO = ClienteDTO.builder()
                .id(cliente.getId())
                .tipoDeDocumentoDeIdentidad(cliente.getTipoDeDocumentoDeIdentidad())
                .numeroDeDocumentoDeIdentidad(cliente.getNumeroDeDocumentoDeIdentidad())
                .razonSocial(cliente.getRazonSocial())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .build();
        return clienteDTO;
    }
}
