package com.lucaser.projeto_grub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lucaser.projeto_grub.model.ClienteDTO;
import com.lucaser.projeto_grub.views.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({Views.PedidoResumo.class})
    private Long id;

    @NotBlank(message = "O nome do cliente não pode estar vazio.")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres.")
    @JsonView({Views.PedidoResumo.class})
    private String nome;

    @CPF(message = "O CPF informado é inválido.")
    @JsonView({Views.PedidoResumo.class})
    private String cpf;

    @NotNull(message = "O gênero deve ser especificado.")
    @Enumerated(EnumType.STRING)
    @JsonView({Views.PedidoResumo.class})
    private GeneroCliente genero;

    @Past(message = "A data de nascimento deve ser uma data passada.")
    @NotNull(message = "A data de nascimento é obrigatória.")
    @JsonView({Views.PedidoResumo.class})
    private Date dataNascimento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties({"produtos", "cliente"})
    @JsonView({Views.PedidoDetalhado.class})
    private List<PedidoEntity> pedidos;

    @JsonView({Views.PedidoDetalhado.class})
    private boolean ativo = true;

    public ClienteEntity(ClienteDTO clienteDTO) {
        this.nome = clienteDTO.nome();
        this.cpf = clienteDTO.cpf();
        this.genero = GeneroCliente.valueOf(clienteDTO.genero());
        this.dataNascimento = clienteDTO.dataNascimento();
    }

    public enum GeneroCliente {
        MASCULINO,
        FEMININO,
        OUTRO;
    }
}
