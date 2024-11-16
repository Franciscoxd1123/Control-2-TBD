package com.example.GestorDeTareas.controllers;

import com.example.GestorDeTareas.models.Usuario;
import com.example.GestorDeTareas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioNew = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok(usuarioNew);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(usuarioRegistrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        try {
            String correo = credenciales.get("correo");
            String contrasena = credenciales.get("contrasena");

            if (correo == null || contrasena == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Correo y contraseña son requeridos");
            }

            Usuario usuario = usuarioService.login(correo, contrasena);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/logout/{idUsuario}")
    public ResponseEntity<?> logout(@PathVariable Long idUsuario) {
        try {
            usuarioService.logout(idUsuario);
            return ResponseEntity.ok()
                    .body("Sesión cerrada exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable int id) {
        Usuario usuario = usuarioService.getUsuario(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario, @PathVariable int id){
        Usuario usuarioUpdated = usuarioService.updateUsuario(usuario, id);
        return ResponseEntity.ok(usuarioUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteUsuario(@PathVariable int id) {
        usuarioService.deleteUsuario(id);
    }
}