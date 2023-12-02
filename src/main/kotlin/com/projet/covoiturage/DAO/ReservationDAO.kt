package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Utilisateur

interface ReservationDAO : DAO<Reservation> {
    fun chercherReservationsParUtilisateur(id: Int): List<Reservation?>?
    fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int): Reservation?
    override fun chercherTous(): List<Reservation>
    override fun chercherParId(id: Int): Reservation?
    override fun ajouter(t: Reservation): Reservation?
    override fun supprimer(id: Int): Boolean
    fun chercherReservationChauffeur(id: Int): Reservation?
    fun accepterReservation(idReservation: Int, idChauffeur: Int): Reservation?
    fun chercherUtilisateur(id: Int): Utilisateur?
    fun validerChauffeur(userId: String): Boolean
    fun validerChauffeurEtId(id: Int, userId: String): Boolean
    fun validerPassagerEtId(id: Int, userId: String): Boolean
    fun validerPassager(userId: String): Boolean
    fun validerReservation(reservation: Reservation, userId: String): Boolean
    fun validerSuppression(idReservation: Int, userId: String): Boolean
}