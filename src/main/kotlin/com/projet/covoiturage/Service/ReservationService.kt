package com.projet.covoiturage.Service

import com.projet.covoiturage.DAO.ReservationDAO
import com.projet.covoiturage.Exception.DroitInsuffisantExc
import com.projet.covoiturage.Exception.NonAuthoriseExc
import com.projet.covoiturage.Model.Reservation
import org.springframework.stereotype.Service

@Service
class ReservationService(val dao: ReservationDAO) {
    fun chercherReservationsParUtilisateur(id: Int, userId: String): List<Reservation?>? {
        if (dao.validerPassagerEtId(id, userId))
            return dao.chercherReservationsParUtilisateur(id)
        else
            throw DroitInsuffisantExc("Seul un passager avec l'id $id peut accéder à ces réservations")
    }

    fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int, userId: String): Reservation? {
        if (dao.validerPassagerEtId(idUtilisateur, userId))
            return dao.chercherReservationParUtilisateur(idReservation, idUtilisateur)
        else
            throw DroitInsuffisantExc("Seul un passager avec l'id $idUtilisateur peut accéder à ces réservations")
    }

    fun chercherTous(userId: String): List<Reservation> {
        if (dao.validerChauffeur(userId)) {
            return dao.chercherTous()
        } else {
            throw DroitInsuffisantExc("Seuls les chauffeurs peuvent accéder aux réservations.")
        }
    }

    fun chercherParId(idReservation: Int) = dao.chercherParId(idReservation)

    fun ajouter(reservation: Reservation, userId: String): Reservation? {
        if (!dao.validerPassager(userId))
            throw DroitInsuffisantExc("Seuls un passager peut ajouter une réservation.")
        if (!dao.validerReservation(reservation, userId))
            throw NonAuthoriseExc("L'utilisateur qui crée la réservation doit être le même que celui dans la réservation.")
        return dao.ajouter(reservation)
    }

    fun supprimer(id: Int, userId: String): Boolean {
        if (!dao.validerPassager(userId))
            throw DroitInsuffisantExc("Seuls un passager peut supprimer une réservation.")
        if (!dao.validerSuppression(id, userId))
            throw NonAuthoriseExc("Seul le propriétaire de cette réservation ")
        return dao.supprimer(id)
    }

    fun chercherReservationChaufeur(idChauffeur: Int, userId: String): Reservation? {
        if (dao.validerChauffeurEtId(idChauffeur, userId)) {
            return dao.chercherReservationChauffeur(idChauffeur)
        } else {
            throw DroitInsuffisantExc("Seuls les chauffeurs peuvent accéder aux réservations.")
        }
    }

    fun accepterReservation(idReservation: Int, idChauffeur: Int, userId: String): Reservation? {
        if (dao.validerChauffeurEtId(idChauffeur, userId)) {
            return dao.accepterReservation(idReservation, idChauffeur)
        } else {
            throw DroitInsuffisantExc("Seuls les chauffeurs peuvent accéder aux réservations.")
        }
    }

    fun chercherUtilisateur(id: Int) = dao.chercherUtilisateur(id)
}