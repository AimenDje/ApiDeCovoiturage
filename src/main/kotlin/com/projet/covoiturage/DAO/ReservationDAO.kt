package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation

interface ReservationDAO : DAO<Reservation> {
    fun chercherReservationsParUtilisateur(id: Int): List<Reservation?>?
    fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int): Reservation?
    override fun chercherTous(): List<Reservation>
    override fun chercherParId(id: Int): Reservation?
    override fun ajouter(t: Reservation): Reservation?
    //fun modifier(t: Reservation): Reservation?
    override fun supprimer(id: Int): Boolean
}