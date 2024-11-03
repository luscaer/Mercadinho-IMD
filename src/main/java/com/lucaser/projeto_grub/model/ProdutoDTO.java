package com.lucaser.projeto_grub.model;

import java.time.LocalDate;

public record ProdutoDTO(
        Long id,
        String nomeProduto,
        String marca,
        LocalDate dataFabricacao,
        LocalDate dataValidade,
        String genero,
        String lote,
        boolean ativo
) {
}
