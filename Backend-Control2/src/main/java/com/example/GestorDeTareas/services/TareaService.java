package com.example.GestorDeTareas.services;

import com.example.GestorDeTareas.models.Tarea;
import com.example.GestorDeTareas.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public Tarea createTarea(Tarea tarea){
        return tareaRepository.create(tarea);
    }

    public List<Tarea> getTareas(){
        return tareaRepository.getAll();
    }

    public Tarea getTarea(int id){
        return tareaRepository.getTareaId(id);
    }

    public Tarea updateTarea(Tarea tarea, int id){
        return tareaRepository.update(tarea, id);
    }

    public void deleteTarea(int id){
        tareaRepository.delete(id);
    }
}
