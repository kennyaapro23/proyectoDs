package com.example.msempresa.repository;

import com.example.msempresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    /**
     * Busca todas las empresas asociadas a un usuario específico.
     *
     * @param usuarioId El ID del usuario.
     * @return Una lista de empresas asociadas al usuario.
     */
    List<Empresa> findByUsuarioId(Integer usuarioId);
}
