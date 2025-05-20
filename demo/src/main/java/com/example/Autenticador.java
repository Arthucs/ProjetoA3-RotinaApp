package com.example;

import java.util.HashMap;
import java.util.Map;

public class Autenticador {
    private final Map<String, String> usuarios = new HashMap<>();

    public Autenticador() {
        usuarios.put("admin", "1234");
        usuarios.put("user", "abcd");
    }

    public boolean autenticar(String usuario, String senha) {
        return usuarios.containsKey(usuario) && usuarios.get(usuario).equals(senha);
    }
}