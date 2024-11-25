package com.example.msempresa.service;

import com.example.msempresa.dto.GestiontrabajosDto;
import com.example.msempresa.entity.Empresa;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para definir las operaciones relacionadas con las empresas.
 */
public interface EmpresaService {

    /**
     * Lista todas las empresas registradas.
     *
     * @return una lista de empresas.
     */
    List<Empresa> list();

    /**
     * Obtiene una empresa por su ID.
     *
     * @param id el identificador único de la empresa.
     * @return un Optional que contiene la empresa si existe, o vacío en caso contrario.
     */
    Optional<Empresa> getById(Integer id);

    /**
     * Guarda una nueva empresa asociada con un usuario.
     *
     * @param empresa los datos de la empresa a registrar.
     * @param userId el ID del usuario asociado.
     * @return la empresa guardada.
     */
    Empresa save(Empresa empresa, Integer userId);

    /**
     * Actualiza los datos de una empresa existente.
     *
     * @param id el identificador único de la empresa a actualizar.
     * @param empresa los nuevos datos de la empresa.
     * @return la empresa actualizada.
     */
    Empresa update(Integer id, Empresa empresa);

    /**
     * Elimina una empresa por su ID.
     *
     * @param id el identificador único de la empresa a eliminar.
     */
    void delete(Integer id);

    /**
     * Publica un nuevo trabajo asociado a una empresa.
     *
     * @param trabajoDto los datos del trabajo a publicar.
     * @param empresaId el ID de la empresa asociada al trabajo.
     * @return los detalles del trabajo publicado.
     */
    GestiontrabajosDto publicarTrabajoDesdeEmpresa(GestiontrabajosDto trabajoDto, Integer empresaId);

    /**
     * Decodifica el token JWT y extrae el ID del usuario.
     *
     * @param token el token JWT recibido en el encabezado Authorization.
     * @return el ID del usuario extraído del token, o null si no se puede decodificar.
     */
    Integer extractUserIdFromToken(String token);

    /**
     * Obtiene una lista de empresas asociadas a un usuario específico.
     *
     * @param userId el ID del usuario.
     * @return una lista de empresas asociadas al usuario.
     */
    List<Empresa> getEmpresasByUserId(Integer userId);
}
