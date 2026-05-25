package com.duoc.msvc_auth.services;

import com.duoc.msvc_auth.clients.UsuarioClient;
import com.duoc.msvc_auth.exceptions.AuthExceptions;
import com.duoc.msvc_auth.models.CuentaAcceso;
import com.duoc.msvc_auth.repositories.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<CuentaAcceso> findAll() {
        return this.authRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public CuentaAcceso findById(Long id) {
        return this.authRepository.findById(id).orElseThrow(
                () -> new AuthExceptions("La cuenta con id: " + id + " no existe")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public CuentaAcceso findByEmail(String email) {
        return this.authRepository.findByEmail(email).orElseThrow(
                () -> new AuthExceptions("La cuenta con email: " + email + " no existe")
        );
    }

    @Transactional
    @Override
    public CuentaAcceso save(CuentaAcceso cuentaAcceso) {
        if (this.authRepository.findByEmail(cuentaAcceso.getEmail()).isPresent()) {
            throw new AuthExceptions("La cuenta con email: " + cuentaAcceso.getEmail() + " ya existe");
        }
        String passwordSec = passwordEncoder.encode(cuentaAcceso.getPasswordHash());
        cuentaAcceso.setPasswordHash(passwordSec);

        return this.authRepository.save(cuentaAcceso);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.authRepository.deleteById(id);
    }

    @Override
    public CuentaAcceso UpdateById(Long id, CuentaAcceso cuentaAcceso) {
        return this.authRepository.findById(id).map(element-> {
            element.setPasswordHash(cuentaAcceso.getPasswordHash());
            element.setEmail(cuentaAcceso.getEmail());
            element.setRol(cuentaAcceso.getRol());
            return  this.authRepository.save(element);
                }).orElseThrow(
                () -> new AuthExceptions("La cuenta con id: " + id + " no existe")
        );
    }
}
