package com.lucaser.projeto_grub.controller;

import com.lucaser.projeto_grub.entity.ClienteEntity;
import com.lucaser.projeto_grub.model.ClienteDTO;
import com.lucaser.projeto_grub.service.ClienteService;
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

    @GetMapping
    public List<ClienteEntity> getAll(){
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClienteEntity> postCliente(@RequestBody ClienteDTO clienteDTO){
        ClienteEntity cliente = clienteService.postCliente(clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/lista")
    public ResponseEntity<List<ClienteEntity>> postClientes(@RequestBody List<ClienteDTO> clientesDTO){
        List<ClienteEntity> savedClientes = clienteService.postClientes(clientesDTO);
        return new ResponseEntity<>(savedClientes, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClienteEntity> putProduto(@RequestBody ClienteDTO clienteDTO){
        ClienteEntity clienteAtualizado = clienteService.putCliente(clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id){
        clienteService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<ClienteEntity> reactivate(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.reactivate(id));
    }
}
