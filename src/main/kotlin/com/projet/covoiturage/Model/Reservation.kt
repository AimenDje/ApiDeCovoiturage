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
        @JoinColumn(name="trajet_id", nullable = true)
        val trajet: Trajet,

        @Column(nullable = true)
        val nombrePassager: Int,

        @Column(nullable = true)
        val heureDepart: Date
) {}