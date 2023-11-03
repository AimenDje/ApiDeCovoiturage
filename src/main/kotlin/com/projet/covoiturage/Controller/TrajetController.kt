package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class TrajetController {
    @Operation(summary = "Obtient tous les trajets")
    @GetMapping("/trajets")
    fun getTrajets(){}

    @Operation(summary = "Obtient un trajet avec son id")
    @GetMapping("/trajet/{id}")
    fun getTrajetParCode(@PathVariable id: Int){}

    @Operation(summary = "Ajouter un trajet")
    @PostMapping("/trajet")
    fun addTrajet(@RequestBody trajet: Trajet){}

    @Operation(summary = "Modifier un trajet avec son id")
    @PutMapping("/trajet/{id}")
    fun modifyTrajet(@RequestBody id: Int, trajet: Trajet){}

    @Operation(summary = "Supprimer un trajet avec son id")
    @DeleteMapping("/trajet/{id}")
    fun deleteTrajet(@PathVariable id: Int){}
}