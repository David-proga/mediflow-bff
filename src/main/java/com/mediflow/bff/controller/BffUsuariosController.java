package com.mediflow.bff.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/usuarios") // El front llamará aquí
public class BffUsuariosController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/test")
    public String getUsuariosTest() {
        // El BFF llama al microservicio de usuarios internamente
        String urlUsuarios = "http://localhost:8080/usuarios/test";
        return restTemplate.getForObject(urlUsuarios, String.class);
    }
}