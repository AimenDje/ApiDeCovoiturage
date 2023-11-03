package com.projet.covoiturage.Model

class Utilisateur (val utilisateurId: Int, val nom: String, val prenom: String, val courriel: String,
                   val telephone: String, val adresse: Adresse, val estPassager: Boolean) {
}