package com.projet.covoiturage.Model

import jakarta.persistence.*
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import java.util.*

@Entity
data class Reservation (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val reservationId: Int,

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name="trajet_id", nullable = false)
        val trajet: Trajet,

        @Column(nullable = false)
        val nombrePassager: Int,

        @Column(nullable = false)
        val heureDepart: Date,

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "chauffeur_id", nullable = true)
        val chauffeur: Utilisateur,

        @Column(columnDefinition = "boolean default false")
        val acceptee: Boolean
) {}