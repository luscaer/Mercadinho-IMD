package com.lucaser.projeto_grub.entity;

import com.lucaser.projeto_grub.model.ClienteDTO;
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

@Entity
@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente não pode estar vazio.")
    @Size(max = 100, message = "O nome do cliente deve ter no máximo 100 caracteres.")
    private String nome;

    @CPF(message = "O CPF informado é inválido.")
    private String cpf;

    @NotNull(message = "O gênero deve ser especificado.")
    @Enumerated(EnumType.STRING)
    private GeneroCliente genero;

    @Past(message = "A data de nascimento deve ser uma data passada.")
    @NotNull(message = "A data de nascimento é obrigatória.")
    private Date dataNascimento;

    private boolean ativo = true;


    public ClienteEntity(ClienteDTO clienteDTO){
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
