package com.lucaser.projeto_grub.entity;

import com.lucaser.projeto_grub.model.ProdutoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "O nome do produto não pode estar vazio.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    private String nomeProduto;

    @NotBlank(message = "A marca do produto não pode estar vazia.")
    @Size(max = 50, message = "A marca do produto deve ter no máximo 50 caracteres.")
    private String marca;

    @NotNull(message = "A data de fabricação é obrigatória.")
    @PastOrPresent(message = "A data de fabricação deve ser uma data passada ou hoje.")
    private LocalDate dataFabricacao;

    @NotNull(message = "A data de validade é obrigatória.")
    @Future(message = "A data de validade deve ser uma data futura.")
    private LocalDate dataValidade;

    @NotNull(message = "O gênero do produto deve ser especificado.")
    @Enumerated(EnumType.STRING)
    private GeneroProduto genero;

    @NotBlank(message = "O lote do produto não pode estar vazio.")
    @Size(max = 20, message = "O lote deve ter no máximo 20 caracteres.")
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

//ADICIONAR UMA RELAÇÃO PRODUTO PEDIDOS: PEDIDOENTITY PEDIDO COM JSON IGNORE
