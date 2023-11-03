package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class ReservationController {
    @Operation(summary = "Obtient toutes les réservations")
    @GetMapping("/reservations")
    fun getReservations(){}

    @Operation(summary = "Obtient une réservation avec son id")
    @GetMapping("/reservation/{id}")
    fun getReservationParCode(@PathVariable id: Int){}

    @Operation(summary = "Ajouter une réservation")
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation){}

    @Operation(summary = "Modifier une réservation avec son id")
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@RequestBody id: Int, reservation: Reservation){}

    @Operation(summary = "Supprimer une réservation avec son id")
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int){}
}