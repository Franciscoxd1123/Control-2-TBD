CREATE DATABASE GestorDeTareas;

DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Tareas;

CREATE TABLE Usuarios(
	idUsuario SERIAL NOT NULL,
	nombre VARCHAR(100),
	correo VARCHAR(100),
	contrasena VARCHAR(100),
	sesionActiva boolean,
	PRIMARY KEY (idUsuario)
);

CREATE TABLE Tareas(
	idTarea SERIAL NOT NULL,
	titulo VARCHAR(100),
	descripcion TEXT,
	estado VARCHAR(20),
	fechaVencimiento DATE,
	idUsuario INT,
	PRIMARY KEY (idTarea),
	FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)
);

-- Tabla Usuarios
INSERT INTO Usuarios (nombre, correo, contrasena, sesionActiva) VALUES
('Juan Perez', 'juan.perez@example.com', 'password123', FALSE),
('Maria Gomez', 'maria.gomez@example.com', 'pass456', FALSE),
('Carlos Diaz', 'carlos.diaz@example.com', 'securePass789', FALSE),
('Ana Torres', 'ana.torres@example.com', 'password987', FALSE),
('Lucia Rojas', 'lucia.rojas@example.com', 'myPass321', FALSE);

-- Tabla Tareas
INSERT INTO Tareas (titulo, descripcion, estado, fechaVencimiento, idUsuario) VALUES
('Revisión de código', 'Revisar el código del último commit', 'pendiente', '2024-11-20', 1),
('Escribir documentación', 'Crear documentación para el módulo de usuarios', 'pendiente', '2024-11-22', 2),
('Reunión con el equipo', 'Reunión semanal de planificación', 'completada', '2024-11-15', 3),
('Pruebas de integración', 'Realizar pruebas de integración en el entorno de staging', 'pendiente', '2024-11-25', 4),
('Actualización del sistema', 'Actualizar el sistema a la versión 2.1', 'completada', '2024-11-30', 5),
('Revisión de seguridad', 'Verificar configuraciones de seguridad', 'pendiente', '2024-12-01', 1),
('Feedback del cliente', 'Recoger feedback del cliente sobre la nueva funcionalidad', 'completada', '2024-11-10', 2),
('Optimización de la base de datos', 'Optimizar consultas para mejorar el rendimiento', 'pendiente', '2024-12-05', 3),
('Limpieza de datos', 'Eliminar registros duplicados y datos obsoletos', 'completada', '2024-11-28', 4),
('Reporte mensual', 'Preparar el reporte de desempeño del mes', 'pendiente', '2024-11-29', 5);

Select *
From Usuarios;

Select *
From Tareas;