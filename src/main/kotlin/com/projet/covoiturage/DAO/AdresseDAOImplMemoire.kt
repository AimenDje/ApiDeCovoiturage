package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Repository.AdresseRepo
import org.springframework.stereotype.Repository

@Repository
class AdresseDAOImplMemoire(val repo: AdresseRepo) : AdresseDAO {
    override fun chercherTous(): List<Adresse> = repo.findAll()

    override fun chercherParId(id: Int): Adresse? = repo.findById(id).get()

    override fun ajouter(t: Adresse): Adresse? = repo.save(t)


    override fun supprimer(id: Int): Boolean {
        repo.deleteById(id)
        return !repo.findById(id).isPresent
    }

}