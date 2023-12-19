package com.projet.covoiturage.Model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Adresse (
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        val adresseId: Int?,
        val numeroCivil: String,
        val appartement: String,
        val rue: String,
        val ville: String,
        val province: String,
        val codePostal: String) {}