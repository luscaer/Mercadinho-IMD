package com.lucaser.projeto_grub.repository;

import com.lucaser.projeto_grub.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{
    List<ProdutoEntity> findAllByAtivoTrue();
}
