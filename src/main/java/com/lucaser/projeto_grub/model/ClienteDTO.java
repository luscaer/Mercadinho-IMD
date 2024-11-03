package com.lucaser.projeto_grub.model;

import java.util.Date;

public record ClienteDTO(
        Long id,
        String nome,
        String cpf,
        String genero,
        Date dataNascimento,
        boolean ativo
) {
}
