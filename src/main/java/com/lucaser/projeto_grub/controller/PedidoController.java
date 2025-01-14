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

    @GetMapping
    @JsonView(Views.PedidoResumo.class)
    public ResponseEntity<List<PedidoEntity>> getAll() {
        List<PedidoEntity> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @JsonView(Views.PedidoDetalhado.class)
    public ResponseEntity<PedidoEntity> getById(@PathVariable Long id) {
        PedidoEntity pedido = pedidoService.getById(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoEntity> postPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoEntity pedido = pedidoService.postPedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @PutMapping
    public ResponseEntity<PedidoEntity> putPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoEntity pedidoAtualizado = pedidoService.putPedido(pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id) {
        pedidoService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<PedidoEntity> reactivate(@PathVariable Long id) {
        PedidoEntity pedidoReativado = pedidoService.reactivate(id);
        return ResponseEntity.ok(pedidoReativado);
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
