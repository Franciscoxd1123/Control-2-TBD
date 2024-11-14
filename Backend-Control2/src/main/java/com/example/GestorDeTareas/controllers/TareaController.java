package com.example.GestorDeTareas.controllers;

import com.example.GestorDeTareas.models.Tarea;
import com.example.GestorDeTareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/")
    public ResponseEntity<Tarea> createTarea(@RequestBody Tarea tarea) {
        Tarea tareaNew = tareaService.createTarea(tarea);
        return ResponseEntity.ok(tareaNew);
    }

    @GetMapping("/")
    public ResponseEntity<List<Tarea>> listTareas() {
        List<Tarea> tareas = tareaService.getTareas();
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTarea(@PathVariable int id) {
        Tarea tarea = tareaService.getTarea(id);
        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Tarea>> buscarTareas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String palabraClave) {
        List<Tarea> tareas = tareaService.buscarTareas(estado, palabraClave);
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/notificaciones/{idUsuario}")
    public ResponseEntity<List<String>> obtenerNotificaciones(@PathVariable int idUsuario) {
        List<String> notificaciones = tareaService.obtenerNotificaciones(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(@RequestBody Tarea tarea, @PathVariable int id){
        Tarea tareaUpdated = tareaService.updateTarea(tarea, id);
        return ResponseEntity.ok(tareaUpdated);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<Tarea> completarTarea(@PathVariable int id) {
        Tarea tareaCompletada = tareaService.completarTarea(id);
        return ResponseEntity.ok(tareaCompletada);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTarea(@PathVariable int id) {
        tareaService.deleteTarea(id);
    }
}