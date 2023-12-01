package com.projet.covoiturage.Model

import jakarta.persistence.*

@Entity
data class Utilisateur (
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        val utilisateurId: Int?,
        val tokenId: String,
        val nom: String,
        val prenom: String,
        val courriel: String,
        val telephone: String,

        @ManyToOne
        @JoinColumn(name="adresse_id", nullable = false)
        val adresse: Adresse?,
        val estPassager: Boolean,
) {}