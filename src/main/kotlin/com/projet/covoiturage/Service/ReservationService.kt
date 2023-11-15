package com.projet.covoiturage.Service

import com.projet.covoiturage.DAO.ReservationDAO
import com.projet.covoiturage.Model.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ReservationService(val dao: ReservationDAO) {
    fun chercherReservationsParUtilisateur(id: Int) = dao.chercherReservationsParUtilisateur(id);
    fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int) = dao.chercherReservationParUtilisateur(idReservation, idUtilisateur);
    fun chercherTous() = dao.chercherTous()
    fun chercherParId(id: Int) = dao.chercherParId(id)
    fun ajouter(reservation: Reservation) = dao.ajouter(reservation)
    fun supprimer(id: Int) = dao.supprimer(id)
    fun chercherReservationChaufeur(id: Int) = dao.chercherReservationChauffeur(id)
    fun accepterReservation(idReservation: Int, idChauffeur: Int) = dao.accepterReservation(idReservation, idChauffeur)

    fun chercherUtilisateur(id: Int) = dao.chercherUtilisateur(id)
}