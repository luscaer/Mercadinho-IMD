package com.lucaser.projeto_grub.service;

import com.lucaser.projeto_grub.entity.ClienteEntity;
import com.lucaser.projeto_grub.model.ClienteDTO;
import com.lucaser.projeto_grub.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteEntity> getAll(){
        return clienteRepository.findAllByAtivoTrue();
    }

    public ClienteEntity getById(Long id){
        return clienteRepository.findById(id).filter(ClienteEntity::isAtivo).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado ou não se encontra ativo no momento."));
    }

    public ClienteEntity postCliente(ClienteDTO clienteDTO){
        ClienteEntity cliente = new ClienteEntity(clienteDTO);
        return clienteRepository.save(cliente);
    }

    public List<ClienteEntity> postClientes(List <ClienteDTO> clientesDto){
        List<ClienteEntity> clientes = clientesDto.stream()
                .map(dto -> {
                    ClienteEntity cliente = new ClienteEntity();
                    cliente.setNome(dto.nome());
                    cliente.setCpf(dto.cpf());
                    cliente.setGenero(ClienteEntity.GeneroCliente.valueOf(dto.genero()));
                    cliente.setDataNascimento(dto.dataNascimento());
                    return cliente;
                })
                .collect(Collectors.toList());

        return clienteRepository.saveAll(clientes);
    }

    public ClienteEntity putCliente(ClienteDTO clienteDTO){
        if (clienteDTO.id() == null) {
            throw new IllegalArgumentException("ID é necessário para atualizar um cliente.");
        }

        ClienteEntity clienteExistente = getById(clienteDTO.id());

        if (clienteDTO.nome() != null) {clienteExistente.setNome(clienteDTO.nome());}
        if (clienteDTO.cpf() != null) {clienteExistente.setCpf(clienteDTO.cpf());}
        if (clienteDTO.genero() != null) {clienteExistente.setGenero(ClienteEntity.GeneroCliente.valueOf(clienteDTO.genero()));}
        if (clienteDTO.dataNascimento() != null) {clienteExistente.setDataNascimento(clienteDTO.dataNascimento());}

        return clienteRepository.save(clienteExistente);
    }

    public ClienteEntity putCliente(ClienteEntity cliente){
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("ID é necessário para atualizar um cliente.");
        }

        ClienteEntity clienteExistente = getById(cliente.getId());

        if (cliente.getNome() != null) {clienteExistente.setNome(cliente.getNome());}
        if (cliente.getCpf() != null) {clienteExistente.setCpf(cliente.getCpf());}
        if (cliente.getGenero() != null) {clienteExistente.setGenero(cliente.getGenero());}
        if (cliente.getDataNascimento() != null) {clienteExistente.setDataNascimento(cliente.getDataNascimento());}

        return clienteRepository.save(clienteExistente);
    }

    public void deleteCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente com ID: " + id + " não encontrado.");
        }

        ClienteEntity cliente = getById(id);

        // Desassocia o cliente de todos os seus pedidos
        cliente.getPedidos().forEach(pedido -> pedido.setCliente(null));

        // A exclusão do cliente e seus pedidos será feita automaticamente
        // devido ao CascadeType.ALL e orphanRemoval = true, mas vamos garantir
        // que os pedidos sejam removidos da lista de pedidos do cliente antes.
        clienteRepository.deleteById(id);
    }

    public void deleteLogic(Long id) {
        ClienteEntity cliente = getById(id);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    //Criei essa função para ser possível reativar um cliente.
    public ClienteEntity reactivate(Long id){
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        if (cliente.isAtivo()){
            throw new IllegalArgumentException("O cliente já se encontra ativo.");
        }

        cliente.setAtivo(true);
        return clienteRepository.save(cliente);
    }
}
