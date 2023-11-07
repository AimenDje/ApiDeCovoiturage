package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Repository.ReservationRepo
import com.projet.covoiturage.Repository.TrajetRepo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TrajetDAOImplMemoire (val db: JdbcTemplate, val repo: TrajetRepo) : TrajetDAO {
    override fun chercherTous(): List<Trajet> = repo.findAll();
    override fun chercherParId(id: Int): Trajet? = repo.findById(id).get()

    override fun ajouter(t: Trajet): Trajet? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        repo.deleteById(id)
        return !repo.findById(id).isPresent
    }

}