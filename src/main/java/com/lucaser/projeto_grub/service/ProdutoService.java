package com.lucaser.projeto_grub.service;

import com.lucaser.projeto_grub.entity.ProdutoEntity;
import com.lucaser.projeto_grub.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoEntity> getAll(){
        return produtoRepository.findAllByAtivoTrue();
    }

    public ProdutoEntity getById(Long id){
        return produtoRepository.findById(id).filter(ProdutoEntity::isAtivo).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado ou não se encontra ativo no momento."));
    }

    public ProdutoEntity postProduto(ProdutoEntity produto){
        return produtoRepository.save(produto);
    }

    public ProdutoEntity putProduto(ProdutoEntity produto){
        ProdutoEntity produtoExistente = getById(produto.getId());

        if (produto.getNomeProduto() != null) {produtoExistente.setNomeProduto(produto.getNomeProduto());}
        if (produto.getMarca() != null) {produtoExistente.setMarca(produto.getMarca());}
        if (produto.getDataFabricacao() != null) {produtoExistente.setDataFabricacao(produto.getDataFabricacao());}
        if (produto.getDataValidade() != null) {produtoExistente.setDataValidade(produto.getDataValidade());}
        if (produto.getGenero() != null) {produtoExistente.setGenero(produto.getGenero());}
        if (produto.getLote() != null) {produtoExistente.setLote(produto.getLote());}

        return produtoRepository.save(produtoExistente);
    }

    public void deleteProduto(Long id){
        produtoRepository.deleteById(id);
    }

    public void deleteLogic(Long id){
        ProdutoEntity produto = getById(id);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    //Criei essa função para ser possível reativar um produto.
    public ProdutoEntity reactivate(Long id){
        ProdutoEntity produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (produto.isAtivo()) {
            throw new IllegalArgumentException("Produto já se encontra ativo.");
        }

        produto.setAtivo(true);
        return produtoRepository.save(produto);
    }
}
