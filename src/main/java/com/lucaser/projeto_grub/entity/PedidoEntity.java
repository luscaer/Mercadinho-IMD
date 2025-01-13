package com.lucaser.projeto_grub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucaser.projeto_grub.views.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.PedidoResumo.class})
    private Long id;

    @NotBlank(message = "O código do pedido não pode estar vazio.")
    @Size(max = 20, message = "O código do pedido deve ter no máximo 20 caracteres.")
    @JsonView({Views.PedidoResumo.class})
    private String codigo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "A lista de produtos não pode estar vazia.")
    @JsonIgnoreProperties({"pedido"})
    @JsonView(Views.PedidoDetalhado.class)
    private List<ProdutoEntity> produtos;

    @ManyToOne
    @NotNull(message = "O cliente deve ser especificado.")
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"pedidos"})
    @JsonView(Views.PedidoDetalhado.class)
    private ClienteEntity cliente;

    @Enumerated(EnumType.STRING)
    @JsonView({Views.PedidoResumo.class})
    private StatusPedido status;

    @JsonView({Views.PedidoDetalhado.class})
    private boolean ativo = true;

    public enum StatusPedido {
        PENDENTE,
        CONFIRMADO,
        CANCELADO,
        ENTREGUE
    }
}
