package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class ReservationController {
    @GetMapping("/reservations")
    fun getReservations(){}

    @GetMapping("/reservation/{id}")
    fun getReservationParCode(@PathVariable id: Int){}

    @GetMapping("/trajet/{id}/reservations")
    fun getReservationsParTrajet(@PathVariable id: Int){}

    @PostMapping("/trajet")
    fun addReservation(@RequestBody reservation: Reservation){}

    @PutMapping("/trajet")
    fun modifyReservation(@RequestBody reservation: Reservation){}

    @DeleteMapping("/trajet/{id}")
    fun deleteReservation(@RequestBody reservation: Reservation){}
}