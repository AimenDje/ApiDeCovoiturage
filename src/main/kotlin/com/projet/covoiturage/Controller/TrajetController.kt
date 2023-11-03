package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class TrajetController {
    @GetMapping("/trajets")
    fun getTrajets(){}

    @GetMapping("/trajet/{id}")
    fun getTrajetParCode(@PathVariable id: Int){}

    @PostMapping("/trajet")
    fun addTrajet(@RequestBody trajet: Trajet){}

    @PutMapping("/trajet/{id}")
    fun modifyTrajet(@RequestBody id: Int, trajet: Trajet){}

    @DeleteMapping("/trajet/{id}")
    fun deleteTrajet(@PathVariable id: Int){}
}