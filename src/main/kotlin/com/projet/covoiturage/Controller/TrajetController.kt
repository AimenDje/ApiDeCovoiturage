package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Service.TrajetService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class TrajetController( val service: TrajetService) {
    @Operation(summary = "Obtient tous les trajets d'un utilisateur")
    @GetMapping("/utilisateur/{id}/trajets")
    fun getReservations() {}

    @Operation(summary = "Obtient un trajet d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun getReservationParCode(@PathVariable idTrajet: Int, @PathVariable idUtilisateur: Int) {}

    @Operation(summary = "Ajouter un trajet")
    @PostMapping("/trajet")
    fun addReservation(@RequestBody trajet: Trajet) = service.ajouter(trajet)

/*    @Operation(summary = "Modifier un trajet avec son id")
    @PutMapping("/trajet/{id}")
    fun modifyTrajet(@RequestBody id: Int, trajet: Trajet){}*/

    @Operation(summary = "Supprimer un trajet avec son id")
    @DeleteMapping("/trajet/{id}")
    fun deleteReservation(@PathVariable id: Int) = service.supprimer(id)
}