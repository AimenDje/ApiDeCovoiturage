package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Adresse

interface AdresseDAO : DAO<Adresse> {
    override fun chercherTous(): List<Adresse>

    override fun chercherParId(id: Int): Adresse?

    override fun ajouter(t: Adresse): Adresse?

    override fun supprimer(id: Int): Boolean
}