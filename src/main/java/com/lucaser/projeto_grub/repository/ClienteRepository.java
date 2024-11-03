package com.lucaser.projeto_grub.repository;

import com.lucaser.projeto_grub.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{
    List<ClienteEntity> findAllByAtivoTrue();
}
