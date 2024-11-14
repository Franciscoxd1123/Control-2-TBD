package com.example.GestorDeTareas.models;

import lombok.Data;
import java.util.Date;

@Data
public class Tarea {

    private Long idTarea;
    private String titulo;
    private String descripcion;
    private String estado;
    private Date fechaVencimiento;
    private int idUsuario;
}