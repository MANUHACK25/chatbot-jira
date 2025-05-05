package com.lodgingApplication.persistence.crud;

import com.lodgingApplication.persistence.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioCrudRepository extends CrudRepository<Usuario, Integer> {


    List<Usuario> findByNOMBREOrderByIdUsuario(String nombre);

    List<Usuario> findByIdUsuario(Integer userId);


}
