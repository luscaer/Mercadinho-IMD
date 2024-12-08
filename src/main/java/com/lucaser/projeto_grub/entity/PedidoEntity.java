package com.lucaser.projeto_grub.entity;

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
    private Long id;

    @NotBlank(message = "O código do pedido não pode estar vazio.")
    @Size(max = 20, message = "O código do pedido deve ter no máximo 20 caracteres.")
    private String codigo;

    @NotEmpty(message = "A lista de produtos não pode estar vazia.")
    @ManyToMany
    private List<ProdutoEntity> produtos;

    @NotNull(message = "O cliente deve ser especificado.")
    @ManyToOne
    private ClienteEntity cliente;

    private boolean ativo = true;
}
