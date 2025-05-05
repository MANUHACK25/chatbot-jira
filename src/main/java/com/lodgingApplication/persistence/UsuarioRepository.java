package com.lodgingApplication.persistence;

import com.lodgingApplication.persistence.crud.UsuarioCrudRepository;
import com.lodgingApplication.persistence.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsuarioRepository {
    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    public List<Usuario> getAllUsuarios(){
        List<Usuario> users = (List<Usuario>) usuarioCrudRepository.findAll();
        return users;
    }

    public List<Usuario> getAllUsersByName(String name){
        return usuarioCrudRepository.findByNOMBREOrderByIdUsuario(name);
    }


}
