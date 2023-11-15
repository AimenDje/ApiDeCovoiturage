package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
class ReservationController(val service: ReservationService) {

    // Chauffeur
    @Operation(summary = "Obtient toutes les réservations disponibles (non acceptées)")
    @GetMapping("/reservations")
    fun getReservations(): List<Reservation> {
        if (service.chercherTous().isEmpty())
            throw ReservationsIntrouvablesExc("Aucune réservation trouvée.")
        return service.chercherTous()
    }

    @Operation(summary = "Obtient une réservation")
    @GetMapping("/reservation/{id}")
    fun getReservationParId(@PathVariable id: Int): Reservation? {
        if (service.chercherParId(id) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $id est introuvable.")
        return service.chercherParId(id)
    }

    @Operation(summary = "Accepter une réservation")
    @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    fun accepteReservation(@PathVariable idReservation: Int, @PathVariable idChauffeur: Int): Reservation? {
        if (service.chercherParId(idReservation) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $idReservation est introuvable.")
        if (service.chercherUtilisateur(idChauffeur) == null)
            throw UtilisateurIntrouvableExc("Le chauffeur avec l'id $idChauffeur est introuvable.")
        if (service.chercherUtilisateur(idChauffeur)!!.estPassager)
            throw NonAuthoriseExc("Cette fonctionnalité est réservée aux chauffeurs.")
        return service.accepterReservation(idReservation, idChauffeur)
    }

    @Operation(summary = "Obtient la réservation acceptée par le chauffeur")
    @GetMapping("/chauffeur/{id}/reservation")
    fun getReservationChauffeur(@PathVariable id: Int): Reservation? {
        if (service.chercherReservationChaufeur(id) == null)
            throw ReservationIntrouvableExc("Aucune réservation acceptée par ce chauffeur.")
        return service.chercherReservationChaufeur(id)
    }


    // Passager
    @Operation(summary = "Obtient toutes les réservations d'un utilisateur")
    @GetMapping("/utilisateur/{id}/reservations")
    fun getReservationsParUtilisateur(@PathVariable id: Int): List<Reservation?>? {
        if (service.chercherReservationsParUtilisateur(id)?.isEmpty() == true)
            throw ReservationIntrouvableExc("La réservation avec l'id $id est introuvable.")
        return service.chercherReservationsParUtilisateur(id)
    }

    @Operation(summary = "Obtient une réservation d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    fun getReservationParUtilisateur(@PathVariable idReservation: Int, @PathVariable idUtilisateur: Int): Reservation? {
        if (service.chercherReservationParUtilisateur(idReservation, idUtilisateur) == null)
            throw ReservationIntrouvableExc(
                "La réservation avec l'id $idReservation est introuvable pour l'utilisateur $idUtilisateur")
        return service.chercherReservationParUtilisateur(idReservation, idUtilisateur)
    }

    @Operation(summary = "Ajouter une réservation")
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation) = service.ajouter(reservation)

    @Operation(summary = "Modifier une réservation avec son id")
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@PathVariable id: Int, @RequestBody reservation: Reservation): Reservation? {
        if (service.chercherParId(id) == null)
            throw MauvaiseRequeteExc("La réservation avec l'id " + reservation.reservationId +
                    " n'existe pas. Utilisez une requête POST pour en ajouter une nouvelle.")
        return service.ajouter(reservation)
    }

    @Operation(summary = "Supprimer une réservation avec son id")
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int): String {
        if (!service.supprimer(id))
            throw ReservationIntrouvableExc("La réservation l'id $id n'a pas pu être supprimée parce qu'elle" +
                    "n'existe pas.")
        return "La réservation avec l'id $id a bien été supprimée."
    }
}