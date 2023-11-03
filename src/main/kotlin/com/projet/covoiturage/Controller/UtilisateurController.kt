package com.projet.covoiturage.Controller

import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Utilisateur
import org.springframework.web.bind.annotation.*

@RestController
class UtilisateurController {
    @GetMapping("/utilisateurs")
    fun getUtilisateurs(){}

    @GetMapping("/utilisateur/{id}")
    fun getUtilisateurParCode(@PathVariable id: Int){}

    @PostMapping("/utilisateur")
    fun addUtilisateur(@RequestBody utilisateur: Utilisateur){}

    @PutMapping("/utilisateur")
    fun modifyUtilisateur(@RequestBody utilisateur: Utilisateur){}

    @DeleteMapping("/utilisateur/{id}")
    fun deleteUtilisateur(@PathVariable id: Int){}
}