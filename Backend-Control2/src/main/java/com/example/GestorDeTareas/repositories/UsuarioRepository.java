package com.example.GestorDeTareas.repositories;

import com.example.GestorDeTareas.models.Usuario;

import java.util.List;

public interface UsuarioRepository {

    Usuario create(Usuario usuario);

    List<Usuario> getAll();

    Usuario getUsuarioId(int id);

    Usuario update(Usuario usuario, int id);

    void delete(int id);
}
