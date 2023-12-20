package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Service.ReservationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("\${api.base-path:}")
@Tag(
    name = "Réservation",
    description = "Points d'accès aux ressources liées aux réservations inscrites au service."
)
class ReservationController(val service: ReservationService) {

    // Chauffeur
    @Operation(
        summary = "Obtient toutes les réservations disponibles (non acceptées)",
        description = "Retourne la liste de toutes les réservations qui n'ont pas été acceptées.",
        operationId = "getReservations",
        responses = [
            ApiResponse(responseCode = "200", description = "Des réservations ont été trouvées."),
            ApiResponse(responseCode = "404", description = "Aucune réservation trouvée dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux chauffeurs.")
        ])
    @GetMapping("/reservations")
    fun getReservations(principal: Principal): List<Reservation> {
        if (service.chercherTous(principal.name).isEmpty())
            throw ReservationsIntrouvablesExc("Aucune réservation trouvée.")
        return service.chercherTous(principal.name)
    }

    @Operation(
        summary = "Obtient une réservation",
        description = "Retourne les informations d'une réservation en la cherchant par son code.",
        operationId = "getReservationParId",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation a été trouvée."),
            ApiResponse(responseCode = "404", description = "La réservation n'existe pas dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux chauffeurs.")
        ]
    )
    @GetMapping("/reservation/{idReservation}")
    fun getReservationParId(@PathVariable idReservation: Int, principal: Principal): Reservation? {
        if (service.chercherParIdChauffeur(idReservation, principal.name) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $idReservation est introuvable.")
        return service.chercherParIdChauffeur(idReservation, principal.name)
    }

    @Operation(
        summary = "Accepter une réservation",
        description = "Accepte une réservation pour un chauffeur spécifique et retourne la réservation acceptée.",
        operationId = "accepteReservation",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation a été acceptée pour le chauffeur spécifié."),
            ApiResponse(responseCode = "404", description = "La réservation n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "Le chauffeur n'existe pas dans le service."),
            ApiResponse(responseCode = "401", description = "La fonctionnalité est réservée aux chauffeurs."),
            ApiResponse(responseCode = "403", description = "Réservé aux chauffeurs.")
        ]
    )
    @PostMapping("/chauffeur/{idChauffeur}/reservation/{idReservation}")
    fun accepteReservation(@PathVariable idReservation: Int, @PathVariable idChauffeur: Int, principal: Principal): Reservation?
    {
        if (service.chercherParId(idReservation) == null)
            throw ReservationIntrouvableExc("La réservation avec l'id $idReservation est introuvable.")
        if (service.chercherUtilisateur(idChauffeur) == null)
            throw UtilisateurIntrouvableExc("Le chauffeur avec l'id $idChauffeur est introuvable.")
        return service.accepterReservation(idReservation, idChauffeur, principal.name)
    }

    @Operation(
        summary = "Obtient la réservation acceptée par le chauffeur",
        description = "Retourne la réservation acceptée par un chauffeur spécifique.",
        operationId = "getReservationChauffeur",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation acceptée par le chauffeur a été trouvée."),
            ApiResponse(responseCode = "404", description = "Le chauffeur n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "Aucune réservation acceptée par ce chauffeur dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux chauffeurs.")
        ]
        )
    @GetMapping("/chauffeur/{idChauffeur}/reservation")
    fun getReservationChauffeur(@PathVariable idChauffeur: Int, principal: Principal): Reservation? {
        if (service.chercherUtilisateur(idChauffeur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idChauffeur est introuvable.")
        else if (service.chercherReservationChaufeur(idChauffeur, principal.name) == null)
            throw ReservationIntrouvableExc("Aucune réservation acceptée par ce chauffeur.")
        return service.chercherReservationChaufeur(idChauffeur, principal.name)
    }


    // Passager
    @Operation(
        summary = "Obtient toutes les réservations d'un passager",
        description = "Retourne les réservations en cherchant par code de passager.",
        operationId = "getReservationsParUtilisateur",
        responses = [
            ApiResponse(responseCode = "200", description = "Les réservations du passager ont étés trouvées."),
            ApiResponse(responseCode = "404", description = "Le passager n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "Aucune réservation trouvée dans le service pour ce passager."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @GetMapping("/utilisateur/{id}/reservations")
    fun getReservationsParUtilisateur(@PathVariable id: Int, principal: Principal): List<Reservation?>? {
        if (service.chercherUtilisateur(id) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $id est introuvable.")
        else if (service.chercherReservationsParUtilisateur(id, principal.name)?.isEmpty() == true)
            throw ReservationsIntrouvablesExc("L'utilisateur avec l'id $id n'a effectué aucune réservation pour le moment.")
        return service.chercherReservationsParUtilisateur(id, principal.name)
    }

    @Operation(
        summary = "Obtient une réservation d'un utilisateur",
        description = "Retourne une réservation cherchant par code de passager & code de réservation",
        operationId = "getReservationParUtilisateur",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation du passager a été trouvée."),
            ApiResponse(responseCode = "404", description = "Le passager n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "La réservation est introuvable pour ce passager."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    fun getReservationParUtilisateur(@PathVariable idReservation: Int, @PathVariable idUtilisateur: Int, principal: Principal): Reservation? {
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if (service.chercherReservationParUtilisateur(idReservation, idUtilisateur, principal.name) == null)
            throw ReservationIntrouvableExc(
                "La réservation avec l'id $idReservation est introuvable pour l'utilisateur $idUtilisateur")
        return service.chercherReservationParUtilisateur(idReservation, idUtilisateur, principal.name)
    }

    @Operation(
        summary = "Ajouter une réservation",
        description = "Crée une nouvelle réservation dans le service puis retourne cette dernière.",
        operationId = "addReservation",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation a été créée dans le service."),
            ApiResponse(responseCode = "400", description = "Mauvaise formulation de la requête d'ajout."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @PostMapping("/reservation")
    fun addReservation(@RequestBody reservation: Reservation, principal: Principal): ResponseEntity<Reservation> {
        val resultat = service.ajouter(reservation, principal.name)
        if (resultat == null)
            throw MauvaiseFormulationObjetExc("La réservation que vous essayez d'ajouter n'est pas formulée " +
                    "correctement.")
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(resultat)
    }

    @Operation(
        summary = "Modifier une réservation avec son id",
        description = "Modifie une réservation existante dans le service puis retourne cette dernière.",
        operationId = "modifyReservation",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation a été modifiée dans le service."),
            ApiResponse(responseCode = "400", description = "La réservation n'existe pas dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @PutMapping("/reservation/{id}")
    fun modifyReservation(@PathVariable id: Int, @RequestBody reservation: Reservation, principal: Principal): Reservation? {
        if (service.chercherParId(id) == null)
            throw MauvaiseRequeteExc("La réservation avec l'id " + reservation.reservationId +
                    " n'existe pas. Utilisez une requête POST pour en ajouter une nouvelle.")
        return service.ajouter(reservation, principal.name)
    }

    @Operation(
        summary = "Supprimer une réservation avec son id",
        description = "Supprime une réservation existante dans le service puis retourne un message.",
        operationId = "deleteReservation",
        responses = [
            ApiResponse(responseCode = "200", description = "La réservation a été supprimée."),
            ApiResponse(responseCode = "404", description = "La réservation n'existe pas dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @DeleteMapping("/reservation/{id}")
    fun deleteReservation(@PathVariable id: Int, principal: Principal): ResponseEntity<String> {
        if (!service.supprimer(id, principal.name))
            throw ReservationIntrouvableExc("La réservation l'id $id n'a pas pu être supprimée parce qu'elle " +
                    "n'existe pas.")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}