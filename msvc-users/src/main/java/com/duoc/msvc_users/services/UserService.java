package com.duoc.msvc_users.services;

import com.duoc.msvc_users.models.Usuario;

import java.util.List;

public interface UserService {
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
    Usuario UpdateById(Long id, Usuario usuario);

}
