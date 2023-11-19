package com.projet.covoiturage.Controller

import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Service.TrajetService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
class TrajetController( val service: TrajetService) {
    @Operation(summary = "Obtient tous les trajets d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun getTrajetsParUtilisateur(@PathVariable idUtilisateur: Int): List<Trajet?>? {
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if (service.chercherTrajetsParUtilisateur(idUtilisateur)?.isEmpty() == true)
            throw TrajetsIntrouvablesExc("L'utilisateur avec l'id $idUtilisateur n'a effectué aucun trajet pour le moment.")
        return service.chercherTrajetsParUtilisateur(idUtilisateur)
    }

    @Operation(summary = "Obtient un trajet d'un utilisateur")
    @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun getTrajetParUtilisateur(@PathVariable idTrajet: Int, @PathVariable idUtilisateur: Int) : Trajet?{
        if (service.chercherUtilisateur(idUtilisateur) == null)
            throw UtilisateurIntrouvableExc("L'utilisateur avec l'id $idUtilisateur est introuvable.")
        else if  (service.chercherTrajetParUtilisateur(idTrajet, idUtilisateur) == null)
            throw TrajetIntrouvableExc("Le trajet avec l'id $idTrajet est introuvable pour l'utilisateur $idUtilisateur")

        return service.chercherTrajetParUtilisateur(idTrajet,idUtilisateur)
    }

    @Operation(summary = "Obtient un trajet avec son id")
    @GetMapping("/trajet/{id}")
    fun getTrajetParId(@PathVariable id: Int): Trajet? {
        if (service.chercherParId(id) == null)
            throw TrajetIntrouvableExc("Le trajet avec l'id $id est introuvable.")
        return service.chercherParId(id)
    }

    @Operation(summary = "Ajouter un trajet")
    @PostMapping("/trajet")
    fun addTrajet(@RequestBody trajet: Trajet) : Trajet?{
        val resultat = service.ajouter(trajet)
        if (resultat == null)
            throw MauvaiseFormulationObjetExc("La trajet que vous essayez d'ajouter est incorrectement formulé ou est manquant")
        else
            return resultat
    }

    @Operation(summary = "Modifier un trajet avec son id")
    @PutMapping("/trajet/{id}")
    fun modifyTrajet(@PathVariable id: Int, @RequestBody trajet: Trajet): Trajet? {
        if (service.chercherParId(id) == null)
            throw MauvaiseRequeteExc("Le trajet avec l'id " + trajet.trajetId +
                    " n'existe pas. Utilisez une requête POST pour en ajouter un nouveau.")
        return service.ajouter(trajet)
    }

    @Operation(summary = "Supprimer un trajet avec son id")
    @DeleteMapping("/trajet/{id}")
    fun deleteTrajet(@PathVariable id: Int): String{
        if (!service.supprimer(id))
            throw TrajetIntrouvableExc("Le trajet avec l'id $id n'a pas pu être supprimé parce qu'il n'existe pas.")
        return "Le trajet avec l'id $id a bien été supprimé."
    }
}