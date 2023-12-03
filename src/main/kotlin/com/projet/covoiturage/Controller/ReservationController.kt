package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("\${api.base-path:}")
@RestController
class ReservationController(val service: ReservationService) {

    // Chauffeur
    @Operation(summary = "Obtient toutes les réservations disponibles (non acceptées)")
    @GetMapping("/reservations")
    fun getReservations(principal: Principal): List<Reservation> {
        if (service.chercherTous(principal.name).isEmpty())
            throw ReservationsIntrouvablesExc("Aucune réservation trouvée.")
        return service.chercherTous(principal.name)
    }

    @Operation(summary = "Obtient une réservation")
    @GetMapping("/reservation/{idReservation}")
    fun getReservationParId(@PathVariable idReservation: Int): Reservation? {
        if (service.chercherParId(idReservation) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $idReservation est introuvable.")
        return service.chercherParId(idReservation)
    }

    @Operation(summary = "Accepter une réservation")
    @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    fun accepteReservation(@PathVariable idReservation: Int, @PathVariable idChauffeur: Int, principal: Principal): Reservation?
    {
        if (service.chercherParId(idReservation) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $idReservation est introuvable.")
        if (service.chercherUtilisateur(idChauffeur) == null)
            throw UtilisateurIntrouvableExc("Le chauffeur avec l'id $idChauffeur est introuvable.")
        if (service.chercherUtilisateur(idChauffeur)!!.estPassager)
            throw NonAuthoriseExc("Cette fonctionnalité est réservée aux chauffeurs.")
        return service.accepterReservation(idReservation, idChauffeur, principal.name)
    }

    @Operation(summary = "Obtient la réservation acceptée par le chauffeur")
    @GetMapping("/chauffeur/{idChauffeur}/reservation")
    fun getReservationChauffeur(@PathVariable idChauffeur: Int, principal: Principal): Reservation? {
        if (service.chercherUtilisateur(idChauffeur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idChauffeur est introuvable.")
        else if (service.chercherReservationChaufeur(idChauffeur, principal.name) == null)
            throw ReservationIntrouvableExc("Aucune réservation acceptée par ce chauffeur.")
        return service.chercherReservationChaufeur(idChauffeur, principal.name)
    }


    // Passager
    @Operation(summary = "Obtient toutes les réservations d'un utilisateur")
    @GetMapping("/utilisateur/{id}/reservations")
    fun getReservationsParUtilisateur(@PathVariable id: Int, principal: Principal): List<Reservation?>? {
        if (service.chercherUtilisateur(id) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $id est introuvable.")
        else if (service.chercherReservationsParUtilisateur(id, principal.name)?.isEmpty() == true)
            throw ReservationsIntrouvablesExc("L'utilisateur avec l'id $id n'a effectué aucune réservation pour le moment.")
        return service.chercherReservationsParUtilisateur(id, principal.name)
    }

    @Operation(summary = "Obtient une réservation d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    fun getReservationParUtilisateur(@PathVariable idReservation: Int, @PathVariable idUtilisateur: Int, principal: Principal): Reservation? {
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if (service.chercherReservationParUtilisateur(idReservation, idUtilisateur, principal.name) == null)
            throw ReservationIntrouvableExc(
                "La réservation avec l'id $idReservation est introuvable pour l'utilisateur $idUtilisateur")
        return service.chercherReservationParUtilisateur(idReservation, idUtilisateur, principal.name)
    }

    @Operation(summary = "Ajouter une réservation")
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation, principal: Principal): Reservation? {
        val resultat = service.ajouter(reservation, principal.name)
        if (resultat == null)
            throw MauvaiseFormulationObjetExc("La réservation que vous essayez d'ajouter n'est pas formulée " +
                    "correctement.")
        else
            return resultat
    }

    @Operation(summary = "Modifier une réservation avec son id")
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@PathVariable id: Int, @RequestBody reservation: Reservation, principal: Principal): Reservation? {
        if (service.chercherParId(id) == null)
            throw MauvaiseRequeteExc("La réservation avec l'id " + reservation.reservationId +
                    " n'existe pas. Utilisez une requête POST pour en ajouter une nouvelle.")
        return service.ajouter(reservation, principal.name)
    }

    @Operation(summary = "Supprimer une réservation avec son id")
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int, principal: Principal): String {
        if (!service.supprimer(id, principal.name))
            throw ReservationIntrouvableExc("La réservation l'id $id n'a pas pu être supprimée parce qu'elle " +
                    "n'existe pas.")
        return "La réservation avec l'id $id a bien été supprimée."
    }
}