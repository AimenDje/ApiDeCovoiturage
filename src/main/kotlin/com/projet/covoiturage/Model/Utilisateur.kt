package com.projet.covoiturage.Model

import jakarta.persistence.*

@Entity
class Utilisateur (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val utilisateurId: Int,
        val nom: String,
        val prenom: String,
        val courriel: String,
        val telephone: String,

        @OneToOne
        @JoinColumn(name="adresse_id", nullable = false)
        val adresse: Adresse,
        val estPassager: Boolean,
) {}