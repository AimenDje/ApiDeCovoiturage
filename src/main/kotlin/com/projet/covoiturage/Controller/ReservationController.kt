package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.MauvaiseRequeteException
import com.projet.covoiturage.Exception.ReservationsIntrouvablesException
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
    @Operation(summary = "Obtient toutes les réservations")
    @GetMapping("/reservations")
    fun getReservations(): List<Reservation> {
        if (service.chercherTous().isNotEmpty())
            return service.chercherTous()
        else
            throw ReservationsIntrouvablesException("Aucune réservation trouvée.")
    }

    @Operation(summary = "Obtient une réservation")
    @GetMapping("/reservation/{id}")
    fun getReservationParId(@PathVariable id: Int) = service.chercherParId(id)

    @Operation(summary = "Obtient toutes les réservations d'un utilisateur")
    @GetMapping("/utilisateur/{id}/reservations")
    fun getReservationsParUtilisateur(@PathVariable id: Int) = service.chercherReservationsParUtilisateur(id)

    @Operation(summary = "Obtient une réservation d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    fun getReservationParUtilisateur(@PathVariable idReservation: Int, @PathVariable idUtilisateur: Int) =
            service.chercherReservationParUtilisateur(idReservation, idUtilisateur)

    @Operation(summary = "Ajouter une réservation")
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation): Reservation? {
        if (service.chercherParId(reservation.reservationId) == null)
            return service.ajouter(reservation)
        else
            throw MauvaiseRequeteException("La réservation avec l'id " + reservation.reservationId +
            " existe déjà. Utilisez une requête PUT pour la modifier.")
    }

    @Operation(summary = "Modifier une réservation avec son id")
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@PathVariable id: Int, reservation: Reservation): Reservation? {
        if (service.chercherParId(id) != null)
            return service.ajouter(reservation)
        else
            throw MauvaiseRequeteException("La réservation avec l'id " + reservation.reservationId +
                    " n'existe pas. Utilisez une requête POST pour en ajouter une nouvelle.")
    }

    @Operation(summary = "Supprimer une réservation avec son id")
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int) = service.supprimer(id)
}