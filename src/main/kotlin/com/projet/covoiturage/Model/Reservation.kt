package com.projet.covoiturage.Model

import jakarta.persistence.*
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import java.util.*

@Entity
data class Reservation (
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        val reservationId: Int?,

        @ManyToOne
        @JoinColumn(name="trajet_id", nullable = false)
        val trajet: Trajet?,

        @Column(nullable = false)
        val nombrePassager: Int?,

        @Column(nullable = false)
        val heureDepart: Date?,

        @ManyToOne
        @JoinColumn(name = "chauffeur_id", nullable = true)
        val chauffeur: Utilisateur?,

        @Column(columnDefinition = "boolean default false", nullable = false)
        val acceptee: Boolean?
) {}