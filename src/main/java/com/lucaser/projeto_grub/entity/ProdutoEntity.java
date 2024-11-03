package com.lucaser.projeto_grub.entity;

import com.lucaser.projeto_grub.model.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProduto;
    private String marca;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    private GeneroProduto genero;

    private String lote;
    private boolean ativo = true;

    public ProdutoEntity(ProdutoDTO produtoDTO) {
        this.nomeProduto = produtoDTO.nomeProduto();
        this.marca = produtoDTO.marca();
        this.dataFabricacao = produtoDTO.dataFabricacao();
        this.dataValidade = produtoDTO.dataValidade();
        this.genero = GeneroProduto.valueOf(produtoDTO.genero());
        this.lote = produtoDTO.lote();
    }

    public enum GeneroProduto {
        COSMETICO,
        ALIMENTICIO,
        HIGIENE_PESSOAL,
        LIMPEZA;
    }
}
