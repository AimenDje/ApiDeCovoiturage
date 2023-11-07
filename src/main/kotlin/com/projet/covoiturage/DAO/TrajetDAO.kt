package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet

interface TrajetDAO : DAO<Trajet> {
    override fun chercherTous(): List<Trajet>
    override fun chercherParId(id: Int): Trajet?
    override fun ajouter(t: Trajet): Trajet?

    override fun supprimer(id: Int): Boolean
}