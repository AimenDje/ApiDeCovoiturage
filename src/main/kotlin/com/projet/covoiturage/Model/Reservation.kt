package com.projet.covoiturage.Model

import java.util.*

data class Reservation (val reservation_id: Int, val trajet: Trajet, val utilisateur: Utilisateur,
                        val nombre_passager: Int, val heure_depart: Date) {
}