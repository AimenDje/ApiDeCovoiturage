package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation

interface ReservationDAO : DAO<Reservation> {
    override fun chercherTous(): List<Reservation>

    override fun chercherParId(id: Int): Reservation?

    override fun ajouter(t: Reservation): Reservation?

    //override fun modifier(t: Reservation): Reservation?

    override fun supprimer(id: Int): Boolean
}