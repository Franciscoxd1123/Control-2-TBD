package com.example.GestorDeTareas.services;

import com.example.GestorDeTareas.models.Usuario;
import com.example.GestorDeTareas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario createUsuario(Usuario usuario){
        return usuarioRepository.create(usuario);
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.getAll();
    }

    public Usuario getUsuario(int id){
        return usuarioRepository.getUsuarioId(id);
    }

    public Usuario updateUsuario(Usuario usuario, int id){
        return usuarioRepository.update(usuario, id);
    }

    public void deleteUsuario(int id){
        usuarioRepository.delete(id);
    }
}
