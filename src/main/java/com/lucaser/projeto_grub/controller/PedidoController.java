package com.lucaser.projeto_grub.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lucaser.projeto_grub.entity.PedidoEntity;
import com.lucaser.projeto_grub.model.PedidoDTO;
import com.lucaser.projeto_grub.service.PedidoService;
import com.lucaser.projeto_grub.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Endpoint para buscar todos os pedidos com a view PedidoResumo
    @GetMapping
    @JsonView(Views.PedidoResumo.class) // Aplica a view PedidoResumo
    public ResponseEntity<List<PedidoEntity>> getAll() {
        List<PedidoEntity> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    // Endpoint para buscar um pedido específico com a view PedidoDetalhado
    @GetMapping("/{id}")
    @JsonView(Views.PedidoDetalhado.class) // Aplica a view PedidoDetalhado
    public ResponseEntity<PedidoEntity> getById(@PathVariable Long id) {
        PedidoEntity pedido = pedidoService.getById(id);
        return ResponseEntity.ok(pedido);
    }

    // Endpoint para criar um novo pedido
    @PostMapping
    public ResponseEntity<PedidoEntity> postPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoEntity pedido = pedidoService.postPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // Endpoint para atualizar um pedido
    @PutMapping
    public ResponseEntity<PedidoEntity> putPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoEntity pedidoAtualizado = pedidoService.putPedido(pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    // Endpoint para deletar um pedido fisicamente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para desativar logicamente um pedido (soft delete)
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id) {
        pedidoService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para reativar um pedido
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<PedidoEntity> reactivate(@PathVariable Long id) {
        PedidoEntity pedidoReativado = pedidoService.reactivate(id);
        return ResponseEntity.ok(pedidoReativado);
    }

    // Endpoint para adicionar um produto a um pedido
    @PatchMapping("/{id}/adicionar-produto/{produtoId}")
    public ResponseEntity<PedidoEntity> adicionarProduto(@PathVariable Long id, @PathVariable Long produtoId) {
        PedidoEntity pedidoAtualizado = pedidoService.adicionarProduto(id, produtoId);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    // Endpoint para remover um produto de um pedido
    @PatchMapping("/{id}/remover-produto/{produtoId}")
    public ResponseEntity<PedidoEntity> removerProduto(@PathVariable Long id, @PathVariable Long produtoId) {
        PedidoEntity pedidoAtualizado = pedidoService.removerProduto(id, produtoId);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}
