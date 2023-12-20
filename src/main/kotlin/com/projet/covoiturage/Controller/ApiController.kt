package com.projet.covoiturage.Controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {
    @GetMapping("/")
    fun index() = "Service web du service de covoiturage"
}