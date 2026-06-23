package com.duoc.msvc_auth.services;

import com.duoc.msvc_auth.models.CuentaAcceso;

import java.util.List;

public interface AuthService {
    List<CuentaAcceso> findAll();
    CuentaAcceso findById(Long id);
    CuentaAcceso findByEmail(String email);
    CuentaAcceso save(CuentaAcceso cuentaAcceso);
    void deleteById(Long id);
    CuentaAcceso UpdateById(Long id, CuentaAcceso cuentaAcceso);
}
