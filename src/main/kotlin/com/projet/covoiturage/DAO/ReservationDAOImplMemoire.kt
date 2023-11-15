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
        repo.updateReservationAccept(idReservation, idChauffeur)
        if (repo.findById(idReservation).isPresent)
            return repo.findById(idReservation).get()
        return null
    }

    override fun chercherUtilisateur(id: Int): Utilisateur? {
        if (repoUser.findById(id).isPresent)
            return repoUser.findById(id).get()
        return null
    }
}