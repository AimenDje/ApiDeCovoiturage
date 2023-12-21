package com.projet.covoiturage.Model

import jakarta.persistence.*

@Entity
data class Trajet(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val trajetId: Int?,
    val nom: String?,

    @ManyToOne
    @JoinColumn(name = "adresseDepart_id", nullable = false)
    val adresseDepart: Adresse?,

    @ManyToOne
    @JoinColumn(name = "adresseArrivee_id", nullable = false)
    val adresseArrivee: Adresse?,

    @ManyToOne()
    @JoinColumn(name = "utilisateur_id", nullable = false)
    val utilisateur: Utilisateur?
)