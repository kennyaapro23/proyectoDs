package com.example.msgestiontrabajos.repository;

import com.example.msgestiontrabajos.entity.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Integer> {
    List<Trabajo> findByEmpresaId(Integer empresaId);
}
