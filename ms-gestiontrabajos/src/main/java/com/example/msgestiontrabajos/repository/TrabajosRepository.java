package com.example.msgestiontrabajos.repository;

import com.example.msgestiontrabajos.entity.Trabajos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabajosRepository extends JpaRepository<Trabajos, Integer> {
}
