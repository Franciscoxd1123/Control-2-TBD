package com.example.GestorDeTareas.services;

import com.example.GestorDeTareas.models.Tarea;
import com.example.GestorDeTareas.repositories.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Tarea completarTarea(int id) {
        Tarea tarea = tareaRepository.getTareaId(id);
        if (tarea == null) {
            throw new IllegalArgumentException("Tarea no encontrada");
        }

        tarea.setEstado("Completada");
        return tareaRepository.update(tarea, id);
    }

    public List<Tarea> buscarTareas(String estado, String palabraClave) {
        return tareaRepository.buscarTareas(estado, palabraClave);
    }

    public List<Tarea> getTareasByUsuario(int idUsuario) {
        return tareaRepository.getTareasByUsuario(idUsuario);
    }

    // Metodo que se ejecutará diariamente para verificar tareas próximas a vencer
    @Scheduled(cron = "0 0 8 * * *") // Ejecutar todos los días a las 8:00 AM
    public List<Tarea> verificarTareasPorVencer() {
        List<Tarea> tareas = tareaRepository.getAll();
        List<Tarea> tareasPorVencer = new ArrayList<>();

        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DAY_OF_YEAR, 3); // 3 días de anticipación
        Date fechaLimite = calendar.getTime();

        for (Tarea tarea : tareas) {
            if (!tarea.getEstado().equals("COMPLETADA") &&
                    tarea.getFechaVencimiento() != null &&
                    tarea.getFechaVencimiento().before(fechaLimite) &&
                    tarea.getFechaVencimiento().after(fechaActual)) {

                tareasPorVencer.add(tarea);
            }
        }

        return tareasPorVencer;
    }

    // Metodo para obtener tareas por vencer para un usuario específico
    public List<String> obtenerNotificaciones(int idUsuario) {
        List<Tarea> tareasPorVencer = verificarTareasPorVencer();
        List<String> notificaciones = new ArrayList<>();

        for (Tarea tarea : tareasPorVencer) {
            long diasRestantes = calcularDiasRestantes(tarea.getFechaVencimiento());
            notificaciones.add("La tarea '" + tarea.getTitulo() + "' vence en " + diasRestantes + " días");
        }

        return notificaciones;
    }

    private long calcularDiasRestantes(Date fechaVencimiento) {
        Date fechaActual = new Date();
        long diferencia = fechaVencimiento.getTime() - fechaActual.getTime();
        return TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);
    }
}