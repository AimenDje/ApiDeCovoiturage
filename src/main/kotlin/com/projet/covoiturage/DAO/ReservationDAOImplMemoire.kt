package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Repository.ReservationRepo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ReservationDAOImplMemoire(val repo: ReservationRepo) : ReservationDAO {
    override fun chercherTous(): List<Reservation> = repo.findAll();

    override fun chercherParId(id: Int): Reservation? = repo.findById(id).get()

    override fun ajouter(t: Reservation): Reservation? = repo.save(t)

    //override fun modifier(t: Reservation): Reservation? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        repo.deleteById(id)
        return !repo.findById(id).isPresent
    }

}