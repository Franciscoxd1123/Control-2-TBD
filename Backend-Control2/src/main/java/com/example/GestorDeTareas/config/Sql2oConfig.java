package com.example.GestorDeTareas.config;

import org.sql2o.Sql2o;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sql2oConfig {

    @Bean
    public Sql2o sql2o() {
        // Configura la conexión a tu base de datos
        String url = "jdbc:postgresql://localhost:5432/GestorDeTareas";
        String username = "postgres";
        String password = "admin";
        return new Sql2o(url, username, password);
    }
}