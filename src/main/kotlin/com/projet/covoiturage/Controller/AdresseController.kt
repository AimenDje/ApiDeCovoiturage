package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Reservation
import org.springframework.web.bind.annotation.*

@RestController
class AdresseController {
    @GetMapping("/adresses")
    fun getAdresses(){}

    @GetMapping("/adresse/{id}")
    fun getAdresseParCode(@PathVariable id: Int){}

    @PostMapping("/adresse")
    fun addAdresse(@RequestBody adresse: Adresse){}

    @PutMapping("/adresse/{id}")
    fun modifyAdresse(@RequestBody id: Int, adresse: Adresse ){}

    @DeleteMapping("/adresse/{id}")
    fun deleteAdresse(@PathVariable id: Int){}
}