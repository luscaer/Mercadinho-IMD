package com.lucaser.projeto_grub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucaser.projeto_grub.model.ProdutoDTO;
import com.lucaser.projeto_grub.views.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "produtos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.PedidoResumo.class})
    private Long id;

    @NotBlank(message = "O nome do produto não pode estar vazio.")
    @Size(max = 100, message = "O nome do produto deve ter no máximo 100 caracteres.")
    @JsonView({Views.PedidoResumo.class})
    private String nomeProduto;

    @NotBlank(message = "A marca do produto não pode estar vazia.")
    @Size(max = 50, message = "A marca do produto deve ter no máximo 50 caracteres.")
    @JsonView({Views.PedidoResumo.class})
    private String marca;

    @NotNull(message = "A data de fabricação é obrigatória.")
    @PastOrPresent(message = "A data de fabricação deve ser uma data passada ou hoje.")
    @JsonView({Views.PedidoResumo.class})
    private LocalDate dataFabricacao;

    @NotNull(message = "A data de validade é obrigatória.")
    @Future(message = "A data de validade deve ser uma data futura.")
    @JsonView({Views.PedidoResumo.class})
    private LocalDate dataValidade;

    @NotNull(message = "O gênero do produto deve ser especificado.")
    @Enumerated(EnumType.STRING)
    @JsonView({Views.PedidoResumo.class})
    private GeneroProduto genero;

    @NotBlank(message = "O lote do produto não pode estar vazio.")
    @Size(max = 20, message = "O lote deve ter no máximo 20 caracteres.")
    @JsonView({Views.PedidoResumo.class})
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonIgnoreProperties({"produtos", "cliente"})
    @JsonView({Views.PedidoDetalhado.class})
    private PedidoEntity pedido;

    @JsonView({Views.PedidoDetalhado.class})
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