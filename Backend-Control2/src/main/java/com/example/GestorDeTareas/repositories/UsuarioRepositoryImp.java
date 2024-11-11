package com.example.GestorDeTareas.repositories;

import com.example.GestorDeTareas.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class UsuarioRepositoryImp implements UsuarioRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public Usuario create(Usuario usuario){
        String sql = "INSERT INTO Usuarios (nombre, correo, contrasena) " +
                "VALUES (:nombre, :correo, :contrasena) " +
                "RETURNING idUsuario";
        try (Connection con = sql2o.open()) {
            Long id = con.createQuery(sql, true)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("correo", usuario.getCorreo())
                    .addParameter("contrasena", usuario.getContrasena())
                    .executeAndFetchFirst(Long.class);

            usuario.setIdUsuario(id);
            return usuario;
        }
        catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Usuario> getAll() {
        String sql = "SELECT * FROM Usuarios";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar los usuarios: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario getUsuarioId(int id){
        String sql = "SELECT * FROM Usuarios WHERE idUsuario = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Usuario.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar el usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Usuario update(Usuario usuario, int id){
        String sql = "UPDATE Usuarios SET nombre = :nombre, correo = :correo, contrasena = :contrasena WHERE idUsuario = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("nombre", usuario.getNombre())
                    .addParameter("correo", usuario.getCorreo())
                    .addParameter("contrasena", usuario.getContrasena())
                    .executeUpdate();
            return usuario;
        }
        catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM Usuarios WHERE idUsuario = :id";

        try (Connection con = sql2o.open()){
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}