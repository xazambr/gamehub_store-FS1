package com.duoc.msvc_users.services;

import com.duoc.msvc_users.exceptions.UserExceptions;
import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new UserExceptions("El usuario con id: "+id+ " no existe")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(
                () -> new UserExceptions("El usuario con email: "+email+ " no existe")
        );
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        if (this.userRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UserExceptions("El usuario con email: "+usuario.getEmail()+ " ya existe");
        }
        return this.userRepository.save(usuario);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Usuario UpdateById(Long id, Usuario usuario) {
        return null;
    }
}
