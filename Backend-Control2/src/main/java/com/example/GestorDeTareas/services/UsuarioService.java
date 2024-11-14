package com.example.GestorDeTareas.services;

import com.example.GestorDeTareas.models.Usuario;
import com.example.GestorDeTareas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

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

    public Usuario registrarUsuario(Usuario usuario) {
        validarCamposRegistro(usuario);
        if (!esCorreoValido(usuario.getCorreo())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }
        if (existeCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }

        return usuarioRepository.create(usuario);
    }

    private void validarCamposRegistro(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }

    private boolean esCorreoValido(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, correo);
    }

    private boolean existeCorreo(String correo) {
        return usuarioRepository.existeCorreo(correo);
    }

    public Usuario login(String correo, String contrasena) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        Usuario usuario = usuarioRepository.findByCorreoAndContrasena(correo, contrasena);
        if (usuario == null) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }
        usuarioRepository.actualizarEstadoSesion(usuario.getIdUsuario(), true);
        usuario.setSesionActiva(true);
        return usuario;
    }

    public void logout(Long idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID de usuario no válido");
        }
        Usuario usuario = usuarioRepository.getUsuarioId(idUsuario.intValue());
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.actualizarEstadoSesion(idUsuario, false);
    }
}