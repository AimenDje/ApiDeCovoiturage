package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Utilisateur

interface UtilisateurDAO : DAO<Utilisateur>  {
    override fun chercherTous(): List<Utilisateur>

    override fun chercherParId(id: Int): Utilisateur?

    override fun ajouter(t: Utilisateur): Utilisateur?

    //override fun modifier(t: Reservation): Reservation?

    override fun supprimer(id: Int): Boolean
}