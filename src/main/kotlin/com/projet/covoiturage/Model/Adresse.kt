package com.projet.covoiturage.Model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Adresse (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val adresseId: Int,
        val numeroCivil: String,
        val appartement: String,
        val rue: String,
        val ville: String,
        val province: String,
        val codePostal: String) {}