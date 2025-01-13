package com.lucaser.projeto_grub.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucaser.projeto_grub.entity.ClienteEntity;
import com.lucaser.projeto_grub.model.ClienteDTO;
import com.lucaser.projeto_grub.service.ClienteService;
import com.lucaser.projeto_grub.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para buscar todos os clientes com a view PedidoResumo
    @GetMapping
    @JsonView(Views.PedidoResumo.class) // Aplica a view PedidoResumo
    public ResponseEntity<List<ClienteEntity>> getAll() {
        List<ClienteEntity> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    // Endpoint para buscar um cliente por ID com a view PedidoDetalhado
    @GetMapping("/{id}")
    @JsonView(Views.PedidoDetalhado.class) // Aplica a view PedidoDetalhado
    public ResponseEntity<ClienteEntity> getById(@PathVariable Long id) {
        ClienteEntity cliente = clienteService.getById(id);
        return ResponseEntity.ok(cliente);
    }

    // Endpoint para criar um novo cliente
    @PostMapping
    public ResponseEntity<ClienteEntity> postCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteEntity cliente = clienteService.postCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    // Endpoint para criar uma lista de clientes
    @PostMapping("/lista")
    public ResponseEntity<List<ClienteEntity>> postClientes(@RequestBody List<ClienteDTO> clientesDTO) {
        List<ClienteEntity> savedClientes = clienteService.postClientes(clientesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClientes);
    }

    // Endpoint para atualizar um cliente com dados do DTO
    @PutMapping
    public ResponseEntity<ClienteEntity> putCliente(@RequestBody ClienteDTO clienteDTO){
        ClienteEntity clienteAtualizado = clienteService.putCliente(clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // Endpoint para deletar um cliente fisicamente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para desativar logicamente um cliente (soft delete)
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id) {
        clienteService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para reativar um cliente
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<ClienteEntity> reactivate(@PathVariable Long id) {
        ClienteEntity clienteReativado = clienteService.reactivate(id);
        return ResponseEntity.ok(clienteReativado);
    }
}
