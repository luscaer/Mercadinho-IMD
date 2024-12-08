package com.lucaser.projeto_grub.controller;

import com.lucaser.projeto_grub.entity.PedidoEntity;
import com.lucaser.projeto_grub.model.PedidoDTO;
import com.lucaser.projeto_grub.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @GetMapping
    public List<PedidoEntity> getAll() { return pedidoService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PedidoEntity> postPedido(@RequestBody PedidoDTO pedidoDTO){
        PedidoEntity pedido = pedidoService.postPedido(pedidoDTO);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping
    public ResponseEntity<PedidoEntity> putPedido(@RequestBody PedidoDTO pedidoDTO){
        PedidoEntity pedidoAtualizado = pedidoService.putPedido(pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id){
        pedidoService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<PedidoEntity> reactivate(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.reactivate(id));
    }

    @PatchMapping("/{id}/adicionar-produto/{produtoId}")
    public ResponseEntity<PedidoEntity> adicionarProduto(@PathVariable Long id, @PathVariable Long produtoId) {
        PedidoEntity pedidoAtualizado = pedidoService.adicionarProduto(id, produtoId);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @PatchMapping("/{id}/remover-produto/{produtoId}")
    public ResponseEntity<PedidoEntity> removerProduto(@PathVariable Long id, @PathVariable Long produtoId) {
        PedidoEntity pedidoAtualizado = pedidoService.removerProduto(id, produtoId);
        return ResponseEntity.ok(pedidoAtualizado);
    }
}
