package com.projet.covoiturage.DAO


import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur

import com.projet.covoiturage.Repository.TrajetRepo
import com.projet.covoiturage.Repository.UtilisateurRepo

import org.springframework.stereotype.Repository

@Repository
class TrajetDAOImplMemoire ( val repo: TrajetRepo, val repoUser: UtilisateurRepo) : TrajetDAO {
    override fun chercherTrajetsParutilisateur(id: Int): List<Trajet?>?  = repo.findAllTrajetsByUser(id)

    override fun chercherTrajetParUtilisateur(idTrajet: Int, idUtilisateur: Int): Trajet? =
    repo.findTrajetByUser(idTrajet, idUtilisateur)

    override fun chercherTous(): List<Trajet> = repo.findAll();
    override fun chercherParId(id: Int): Trajet? {
        if (repo.findById(id).isPresent)
            return repo.findById(id).get()
        return null
    }

    override fun ajouter(t: Trajet): Trajet? = repo.save(t)

    override fun supprimer(id: Int): Boolean {
        if (repo.findById(id).isPresent) {
            repo.deleteById(id)
            return true
        } else
            return false
    }
    override fun chercherUtilisateur(id: Int): Utilisateur? {
        if (repoUser.findById(id).isPresent)
            return repoUser.findById(id).get()
        return null
    }

}