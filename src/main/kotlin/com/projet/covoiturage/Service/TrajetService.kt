package com.projet.covoiturage.Service

import com.projet.covoiturage.DAO.TrajetDAO
import com.projet.covoiturage.Exception.DroitInsuffisantExc
import com.projet.covoiturage.Exception.NonAuthoriseExc
import com.projet.covoiturage.Model.Trajet
import org.springframework.stereotype.Service

@Service
class TrajetService(val dao: TrajetDAO) {
    fun chercherTrajetsParUtilisateur(id: Int, userId: String): List<Trajet?>? {
        if (dao.validerPassagerEtId(id, userId))
            return dao.chercherTrajetsParutilisateur(id)
        else
            throw DroitInsuffisantExc("Seul le passager avec l'id $id peut consulter ses trajets")
    }

    fun chercherTrajetParUtilisateur(idTrajet: Int, idUtilisateur: Int, userId: String): Trajet? {
        if (dao.validerPassagerEtId(idUtilisateur, userId))
            return dao.chercherTrajetParUtilisateur(idTrajet, idUtilisateur)
        else
            throw DroitInsuffisantExc("Seul un passager avec l'id $idUtilisateur peut accéder à ses trajets")
    }

    fun chercherTous() = dao.chercherTous()

    fun chercherParId(id: Int) = dao.chercherParId(id)

    fun ajouter(trajet: Trajet, userId: String): Trajet? {
        if (!dao.validerPassager(userId))
            throw DroitInsuffisantExc("Seul un passager peut ajouter un trajet.")
        if (!dao.validerTrajet(trajet, userId))
            throw NonAuthoriseExc("L'utilisateur qui crée le trajet doit être le même que celui dans le trajet.")

        return dao.ajouter(trajet)
    }

    fun supprimer(id: Int, userId: String): Boolean {
        if (!dao.validerPassager(userId))
            throw DroitInsuffisantExc("Seul un passager peut supprimer un trajet.")
        if (!dao.validerSuppression(id, userId))
            throw NonAuthoriseExc("Seul le propriétaire de ce trajet  ")
        return dao.supprimer(id)
    }

    fun chercherUtilisateur(id: Int) = dao.chercherUtilisateur(id)
}