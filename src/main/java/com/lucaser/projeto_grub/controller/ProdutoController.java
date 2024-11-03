package com.lucaser.projeto_grub.controller;

import com.lucaser.projeto_grub.entity.ProdutoEntity;
import com.lucaser.projeto_grub.model.ProdutoDTO;
import com.lucaser.projeto_grub.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<ProdutoEntity> getAll(){
        return produtoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEntity> getById(@PathVariable Long id){
        ProdutoEntity produto = produtoService.getById(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoEntity> postProduto(@RequestBody ProdutoDTO produtoDTO){
        ProdutoEntity produto = new ProdutoEntity(produtoDTO);
        ProdutoEntity novoProduto = produtoService.postProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @PutMapping
    public ResponseEntity<ProdutoEntity> putProduto(@RequestBody ProdutoEntity produtoEntity){
        ProdutoEntity produtoAtualizado = produtoService.putProduto(produtoEntity);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> deletarLogic(@PathVariable Long id){
        produtoService.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<ProdutoEntity> reactivate(@PathVariable Long id) {
        ProdutoEntity produtoReativado = produtoService.reactivate(id);
        return ResponseEntity.ok(produtoReativado);
    }
}
