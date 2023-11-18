package com.projet.covoiturage.Service

import com.projet.covoiturage.DAO.TrajetDAO
import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur
import org.springframework.stereotype.Service

@Service
class TrajetService (val dao: TrajetDAO){
    fun chercherTrajetsParUtilisateur(idUtilisateur: Int) = dao.chercherTrajetsParutilisateur(idUtilisateur)
    fun chercherTrajetParUtilisateur(idTrajet: Int, idUtilisateur: Int) = dao.chercherTrajetParUtilisateur(idTrajet, idUtilisateur)
    fun chercherTous() = dao.chercherTous()

    fun chercherParId(id: Int) = dao.chercherParId(id)

    fun ajouter(trajet: Trajet) = dao.ajouter(trajet)

    fun supprimer(id: Int) = dao.supprimer(id)
}