package com.example.GestorDeTareas.repositories;

import com.example.GestorDeTareas.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class TareaRepositoryImp implements TareaRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Tarea create(Tarea tarea){
        String sql = "INSERT INTO Tareas (titulo, descripcion, estado, fechaVencimiento) " +
                "VALUES (:titulo, :descripcion, :estado, :fechaVencimiento) " +
                "RETURNING idTarea";
        try (Connection con = sql2o.open()) {
            Long id = con.createQuery(sql, true)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("fechaVencimiento", tarea.getFechaVencimiento())
                    .executeAndFetchFirst(Long.class);

            tarea.setIdTarea(id);
            return tarea;
        }
        catch (Exception e) {
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
        String sql = "SELECT * FROM Tareas WHERE idTarea = :id";

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
        String sql = "UPDATE Tareas SET titulo = :titulo, descripcion = :descripcion, estado = :estado, fechaVencimiento = :fechaVencimiento WHERE idTarea = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("estado", tarea.getEstado())
                    .addParameter("fechaVencimiento", tarea.getFechaVencimiento())
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
        String sql = "DELETE FROM Tareas WHERE idTarea = :id";

        try (Connection con = sql2o.open()){
            con.createQuery(sql).addParameter("id",id).executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error al eliminar la tarea: " + e.getMessage());
        }
    }
}