package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Utilisateur
import com.projet.covoiturage.Repository.UtilisateurRepo
import org.springframework.stereotype.Repository

@Repository
class UtilisateurDAOImplMemoire(val repo: UtilisateurRepo) : UtilisateurDAO {
    override fun chercherTous(): List<Utilisateur> = repo.findAll()

    override fun chercherParId(id: Int): Utilisateur? = repo.findById(id).get()

    override fun ajouter(t: Utilisateur): Utilisateur? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        repo.deleteById(id)
        return !repo.findById(id).isPresent
    }

}