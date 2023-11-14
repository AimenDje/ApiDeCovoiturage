package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Repository.ReservationRepo
import org.springframework.stereotype.Repository

@Repository
class ReservationDAOImplMemoire(val repo: ReservationRepo) : ReservationDAO {
    override fun chercherReservationsParUtilisateur(id: Int): List<Reservation?>? = repo.findAllReservationByUser(id)
    override fun chercherReservationParUtilisateur(idReservation: Int, idUtilisateur: Int): Reservation? =
            repo.findReservationByUserAndReservation(idReservation, idUtilisateur)

    override fun chercherTous(): List<Reservation> = repo.findAll();

    override fun chercherParId(id: Int): Reservation? = repo.findById(id).get()

    override fun ajouter(t: Reservation): Reservation? = repo.save(t)

    //override fun modifier(t: Reservation): Reservation? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        repo.deleteById(id)
        return !repo.findById(id).isPresent
    }

}