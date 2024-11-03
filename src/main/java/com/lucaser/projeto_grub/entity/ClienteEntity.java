package com.lucaser.projeto_grub.entity;

import com.lucaser.projeto_grub.model.ClienteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String nome;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private GeneroCliente genero;

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
