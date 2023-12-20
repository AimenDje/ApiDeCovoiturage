package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Utilisateur
import com.projet.covoiturage.Repository.ReservationRepo
import com.projet.covoiturage.Repository.UtilisateurRepo
import org.springframework.stereotype.Repository

@Repository
class ReservationDAOImplMemoire(val repo: ReservationRepo, val repoUser: UtilisateurRepo) : ReservationDAO {
    override fun chercherReservationsParUtilisateur(id: Int): List<Reservation?>? = repo.findAllReservationByUser(id)

    override fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int): Reservation? =
            repo.findReservationByUserAndReservation(idReservation, idUtilisateur)

    override fun chercherTous(): List<Reservation> = repo.findReservationsByAccepteeIsFalse();

    override fun chercherParId(id: Int): Reservation? {
        if (repo.findById(id).isPresent)
            return repo.findById(id).get()
        return null
    }

    override fun ajouter(t: Reservation): Reservation? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        if (repo.findById(id).isPresent) {
            repo.deleteById(id)
            return true
        } else
            return false
    }

    override fun chercherReservationChauffeur(id: Int): Reservation? =
        repo.findReservationByChauffeurUtilisateurIdAndAccepteeIsTrue(id);

    override fun accepterReservation(idReservation: Int, idChauffeur: Int): Reservation? {
        val reservation = repo.findById(idReservation).get()
        if (reservation.acceptee == true) {
            return null
        }
        val reservationRetour = repo.findById(idReservation).get()
        repo.updateReservationAccept(idReservation, idChauffeur)
        return reservationRetour
    }

    override fun chercherUtilisateur(id: Int): Utilisateur? {
        if (repoUser.findById(id).isPresent)
            return repoUser.findById(id).get()
        return null
    }

    override fun validerChauffeur(userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        return !user.estPassager
    }

    override fun validerChauffeurEtId(id: Int, userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        return !user.estPassager && user.utilisateurId == id
    }

    override fun validerPassagerEtId(id: Int, userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        return user.estPassager && user.utilisateurId == id
    }

    override fun validerPassager(userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        return user.estPassager
    }

    override fun validerReservation(reservation: Reservation, userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        return user.utilisateurId == reservation.trajet?.utilisateur?.utilisateurId
    }

    override fun validerSuppression(idReservation: Int, userId: String): Boolean {
        val user = repoUser.findUtilisateurByTokenId(userId)
        val reservation = repo.findById(idReservation)
        return user.utilisateurId == reservation.get().trajet?.utilisateur?.utilisateurId
    }

}