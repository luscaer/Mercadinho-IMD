package com.lucaser.projeto_grub.service;

import com.lucaser.projeto_grub.entity.ClienteEntity;
import com.lucaser.projeto_grub.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ClienteEntity postCliente(ClienteEntity cliente){
        return clienteRepository.save(cliente);
    }

    public ClienteEntity putCliente(ClienteEntity cliente){
        ClienteEntity clienteExistente = getById(cliente.getId());

        if (cliente.getNome() != null) {clienteExistente.setNome(cliente.getNome());}
        if (cliente.getCpf() != null) {clienteExistente.setCpf(cliente.getCpf());}
        if (cliente.getGenero() != null) {clienteExistente.setGenero(cliente.getGenero());}
        if (cliente.getDataNascimento() != null) {clienteExistente.setDataNascimento(cliente.getDataNascimento());}

        return clienteRepository.save(clienteExistente);
    }

    public void deleteCliente(Long id){
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
