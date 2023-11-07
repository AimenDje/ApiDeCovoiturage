package com.projet.covoiturage.Model

import jakarta.persistence.*

@Entity
class Trajet (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val trajetId: Int,
        val nom :String,

        @OneToOne
        @JoinColumn(name="adresseDepart_id", nullable = false)
        val adresseDepart: Adresse,

        @OneToOne
        @JoinColumn(name="adresseArrivee_id", nullable = false)
        val adresseArrivee: Adresse) {}