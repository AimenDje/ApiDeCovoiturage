package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Service.TrajetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class TrajetController(val service: TrajetService) {

    @Operation(
        summary = "Obtient tous les trajets d'un passager",
        description = "Retourne les trajets en cherchant par code de passager.",
        operationId = "getTrajetsParUtilisateur",
        responses = [
            ApiResponse(responseCode = "200", description = "Les trajets du passager ont étés trouvées."),
            ApiResponse(responseCode = "404", description = "Le passager n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "Aucun trajet trouvé dans le service pour ce passager."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun getTrajetsParUtilisateur(@PathVariable idUtilisateur: Int, principal: Principal): List<Trajet?>? {
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if (service.chercherTrajetsParUtilisateur(idUtilisateur, principal.name)?.isEmpty() == true)
            throw TrajetsIntrouvablesExc("L'utilisateur avec l'id $idUtilisateur n'a effectué aucun trajet pour le moment.")
        return service.chercherTrajetsParUtilisateur(idUtilisateur, principal.name)
    }


    @Operation(
        summary = "Obtient un trajet d'un passager",
        description = "Retourne un trajet en cherchant par code de passager & code de trajet",
        operationId = "getTrajetParUtilisateur",
        responses = [
            ApiResponse(responseCode = "200", description = "Le trajet du passager ont été trouvé."),
            ApiResponse(responseCode = "404", description = "Le passager n'existe pas dans le service."),
            ApiResponse(responseCode = "404", description = "Le trajet est introuvable pour ce passager."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun getTrajetParUtilisateur(
        @PathVariable idTrajet: Int,
        @PathVariable idUtilisateur: Int,
        principal: Principal
    ): Trajet? {
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if (service.chercherTrajetParUtilisateur(idTrajet, idUtilisateur, principal.name) == null)
            throw TrajetIntrouvableExc("Le trajet avec l'id $idTrajet est introuvable pour l'utilisateur $idUtilisateur")

        return service.chercherTrajetParUtilisateur(idTrajet, idUtilisateur, principal.name)
    }


    @Operation(
        summary = "Ajouter un trajet",
        description = "Crée un nouveau trajet dans le service puis retourne cet dernier.",
        operationId = "addTrajet",
        responses = [
            ApiResponse(responseCode = "200", description = "Le trajet a été créé dans le service."),
            ApiResponse(responseCode = "400", description = "Mauvaise formulation de la requête d'ajout."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @PostMapping("/trajet")
    fun addTrajet(@RequestBody trajet: Trajet, principal: Principal): ResponseEntity<Trajet> {
        val resultat = service.ajouter(trajet, principal.name)
        if (resultat == null)
            throw MauvaiseFormulationObjetExc("La trajet que vous essayez d'ajouter est incorrectement formulé ou est manquant")
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(resultat)
    }


    @Operation(
        summary = "Modifier un trajet avec son id",
        description = "Modifie un trajet existant dans le service puis retourne cette dernière.",
        operationId = "modifyTrajet",
        responses = [
            ApiResponse(responseCode = "200", description = "Le trajet a été modifié dans le service."),
            ApiResponse(responseCode = "400", description = "Le trajet n'existe pas dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @PutMapping("/trajet/{id}")
    fun modifyTrajet(@PathVariable id: Int, @RequestBody trajet: Trajet, principal: Principal): Trajet? {
        if (service.chercherParId(id) == null)
            throw MauvaiseRequeteExc(
                "Le trajet avec l'id " + trajet.trajetId +
                        " n'existe pas. Utilisez une requête POST pour en ajouter un nouveau."
            )
        return service.ajouter(trajet, principal.name)
    }


    @Operation(
        summary = "Supprimer un trajet avec son id",
        description = "Supprime un trajet existante dans le service puis retourne un message.",
        operationId = "deleteTrajet",
        responses = [
            ApiResponse(responseCode = "200", description = "Le trajet a été supprimée."),
            ApiResponse(responseCode = "404", description = "Le trajet n'existe pas dans le service."),
            ApiResponse(responseCode = "403", description = "Réservé aux passagers.")
        ]
    )
    @DeleteMapping("/trajet/{id}")
    fun deleteTrajet(@PathVariable id: Int, principal: Principal): ResponseEntity<String> {
        if (!service.supprimer(id, principal.name))
            throw TrajetIntrouvableExc("Le trajet avec l'id $id n'a pas pu être supprimé parce qu'il n'existe pas.")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}