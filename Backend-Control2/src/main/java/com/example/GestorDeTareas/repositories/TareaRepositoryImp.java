package com.example.GestorDeTareas.repositories;

import com.example.GestorDeTareas.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TareaRepositoryImp implements TareaRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Tarea create(Tarea tarea){
        try (Connection con = sql2o.open()) {
            // Primero insertar y obtener el ID
            String sql = "INSERT INTO Tareas (titulo, descripcion, estado, fechavencimiento, idusuario) " +
                    "VALUES (:titulo, :descripcion, :estado, :fechavencimiento, :idusuario) " +
                    "RETURNING idtarea";

            Integer idTarea = con.createQuery(sql, true)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("fechavencimiento", tarea.getFechaVencimiento())
                    .addParameter("idusuario", tarea.getIdUsuario())
                    .executeUpdate()
                    .getKey(Integer.class);

            tarea.setIdTarea(Long.valueOf(idTarea));
            return tarea;

        } catch (Exception e) {
            System.out.println("Error al crear la tarea: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getAll() {
        String sql = "SELECT * FROM Tareas";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Tarea.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar las tareas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea getTareaId(int id){
        String sql = "SELECT * FROM Tareas WHERE idtarea = :id";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).addParameter("id",id).executeAndFetchFirst(Tarea.class);
        }
        catch (Exception e) {
            System.out.println("Error al consultar la tarea: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea update(Tarea tarea, int id){
        String sql = "UPDATE Tareas SET titulo = :titulo, descripcion = :descripcion, estado = :estado, fechavencimiento = :fechavencimiento, idusuario = :idusuario WHERE idtarea = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("fechavencimiento", tarea.getFechaVencimiento())
                    .addParameter("idusuario", tarea.getIdUsuario())
                    .executeUpdate();
            return tarea;
        }
        catch (Exception e) {
            System.out.println("Error al actualizar la tarea: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id){
        String sql = "DELETE FROM Tareas WHERE idtarea = :id";

        try (Connection con = sql2o.open()){
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error al eliminar la tarea: " + e.getMessage());
        }
    }

    @Override
    public List<Tarea> buscarTareas(String estado, String palabraClave) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Tareas WHERE 1=1");

        if (estado != null && !estado.isEmpty()) {
            sql.append(" AND estado = :estado");
        }

        if (palabraClave != null && !palabraClave.isEmpty()) {
            sql.append(" AND (LOWER(titulo) LIKE LOWER(:palabraClave) OR LOWER(descripcion) LIKE LOWER(:palabraClave))");
        }

        try (Connection con = sql2o.open()) {
            Query query = con.createQuery(sql.toString());

            if (estado != null && !estado.isEmpty()) {
                query.addParameter("estado", estado);
            }

            if (palabraClave != null && !palabraClave.isEmpty()) {
                query.addParameter("palabraClave", "%" + palabraClave + "%");
            }
            return query.executeAndFetch(Tarea.class);

        } catch (Exception e) {
            System.out.println("Error al buscar tareas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getTareasByUsuario(int idUsuario) {
        String sql = "SELECT * FROM Tareas WHERE idusuario = :idUsuario";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("idUsuario", idUsuario)
                    .executeAndFetch(Tarea.class);

        } catch (Exception e) {
            System.out.println("Error al consultar las tareas del usuario: " + e.getMessage());
            return null;
        }
    }
}