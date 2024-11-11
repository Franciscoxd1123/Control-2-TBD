package com.example.GestorDeTareas.repositories;

import com.example.GestorDeTareas.models.Tarea;

import java.util.List;

public interface TareaRepository {

    Tarea create(Tarea tarea);

    List<Tarea> getAll();

    Tarea getTareaId(int id);

    Tarea update(Tarea tarea, int id);

    void delete(int id);
}
