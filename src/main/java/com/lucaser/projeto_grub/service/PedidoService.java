package com.lucaser.projeto_grub.service;

import com.lucaser.projeto_grub.entity.ClienteEntity;
import com.lucaser.projeto_grub.entity.PedidoEntity;
import com.lucaser.projeto_grub.entity.ProdutoEntity;
import com.lucaser.projeto_grub.model.PedidoDTO;
import com.lucaser.projeto_grub.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    ProdutoService produtoService;

    public List<PedidoEntity> getAll() {
        return pedidoRepository.findAllByAtivoTrue();
    }

    public PedidoEntity getById(Long id) {
        return pedidoRepository.findById(id).filter(PedidoEntity::isAtivo).orElseThrow(() -> new RuntimeException("Pedido não encontrado, ou não se encontra ativo, com ID: " + id));
    }

    public PedidoEntity toEntity(PedidoDTO pedidoDTO) {
        ClienteEntity cliente = clienteService.getById(pedidoDTO.clienteId());

        List<ProdutoEntity> produtos = pedidoDTO.produtosIds().stream()
                .map(produtoService::getById)
                .toList();

        PedidoEntity pedido = PedidoEntity.builder()
                .id(pedidoDTO.id())
                .codigo(pedidoDTO.codigo())
                .produtos(produtos)
                .cliente(cliente)
                .status(PedidoEntity.StatusPedido.PENDENTE)
                .ativo(true)
                .build();

        produtos.forEach(produto -> {
            produto.setPedido(pedido); // Configura a relação bidirecional
        });

        pedido.setProdutos(produtos);

        return pedido;
    }

    public PedidoEntity postPedido(PedidoDTO pedidoDTO){
        return pedidoRepository.save(toEntity(pedidoDTO));
    }

    public PedidoEntity putPedido(PedidoDTO pedidoDTO) {
        if (pedidoDTO.id() == null) {
            throw new IllegalArgumentException("ID é necessário para atualizar um pedido.");
        }

        PedidoEntity pedidoExistente = getById(pedidoDTO.id());

        if (pedidoDTO.codigo() != null) {
            pedidoExistente.setCodigo(pedidoDTO.codigo());
        }

        if (pedidoDTO.clienteId() != null) {
            ClienteEntity cliente = clienteService.getById(pedidoDTO.clienteId());
            pedidoExistente.setCliente(cliente);
        }

        if (pedidoDTO.produtosIds() != null && !pedidoDTO.produtosIds().isEmpty()) {
            List<ProdutoEntity> produtos = pedidoDTO.produtosIds().stream()
                    .map(produtoService::getById)
                    .toList();
            pedidoExistente.setProdutos(produtos);
        }

        return pedidoRepository.save(pedidoExistente);
    }

    public void deletePedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        pedidoRepository.deleteById(id);
    }

    public void deleteLogic(Long id) {
        PedidoEntity pedido = getById(id);
        pedido.setAtivo(false);

        pedido.getProdutos().forEach(produto -> produto.setAtivo(false));

        pedidoRepository.save(pedido);
    }

    public PedidoEntity reactivate(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

        if (pedido.isAtivo()){
            throw new IllegalArgumentException("O pedido já se encontra ativo.");
        }

        pedido.setAtivo(true);
        return pedidoRepository.save(pedido);
    }

    public PedidoEntity adicionarProduto(Long id, Long produtoId) {
        PedidoEntity pedido = getById(id);
        ProdutoEntity produto = produtoService.getById(produtoId);

        produto.setPedido(pedido);

        pedido.getProdutos().add(produto);

        return pedidoRepository.save(pedido);
    }

    public PedidoEntity removerProduto(Long id, Long produtoId) {
        PedidoEntity pedido = getById(id);
        ProdutoEntity produto = produtoService.getById(produtoId);

        produto.setPedido(null);

        pedido.getProdutos().remove(produto);
        return pedidoRepository.save(pedido);
    }
}
