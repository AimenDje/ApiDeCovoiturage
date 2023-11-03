package com.projet.covoiturage.Model

class Trajet (
        val trajetId: Int,
        val nom :String,
        val adresseDepart: Adresse,
        val adresseArrivee: Adresse) {}