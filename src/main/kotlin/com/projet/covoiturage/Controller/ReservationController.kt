package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
class ReservationController {
    @GetMapping("/reservations")
    fun getReservations(){}

    @GetMapping("/reservation/{id}")
    fun getReservationParCode(@PathVariable id: Int){}

    @GetMapping("/trajet/{id}/reservations")
    fun getReservationsParTrajet(@PathVariable id: Int){}

    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation){}

    @PutMapping("/reservation")
    fun modifyReservation(@RequestBody reservation: Reservation){}

    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int){}
}