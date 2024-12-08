package com.lucaser.projeto_grub.model;

import java.util.List;

public record PedidoDTO(
        Long id,
        String codigo,
        List<Long> produtosIds,
        Long clienteId,
        boolean ativo
) {}
