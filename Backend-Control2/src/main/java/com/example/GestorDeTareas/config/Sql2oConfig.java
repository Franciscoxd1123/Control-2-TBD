package com.example.GestorDeTareas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;

@Configuration
public class Sql2oConfig {

    @Bean
    public Sql2o sql2o() {
        return new Sql2o("jbdc:postgresql://localhost:5432/GestorDeTareas", "username", "pasword");
    }

}