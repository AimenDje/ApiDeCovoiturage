package com.projet.covoiturage.Model

import java.util.*

data class Reservation (
        val reservationId: Int,
        val trajet: Trajet,
        val utilisateur: Utilisateur,
        val nombrePassager: Int,
        val heureDepart: Date) {}