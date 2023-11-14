package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class ReservationController(val service: ReservationService) {
    @Operation(summary = "Obtient toutes les réservations d'un utilisateur")
    @GetMapping("/utilisateur/{id}/reservations")
    fun getReservationsParUtilisateur() {}

    @Operation(summary = "Obtient une réservation d'un utilisateur")
    @GetMapping("/utilisateur/{id}/reservation/{id}")
    fun getReservationParUtilisateur(@PathVariable id: Int) {}

    @Operation(summary = "Ajouter une réservation")
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation) = service.ajouter(reservation)

/*    @Operation(summary = "Modifier une réservation avec son id")
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@PathVariable id: Int, reservation: Reservation) = service.modifier(reservation)*/

    @Operation(summary = "Supprimer une réservation avec son id")
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int) = service.supprimer(id)
}