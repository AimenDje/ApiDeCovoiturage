package com.projet.covoiturage.DAO

import com.projet.covoiturage.Model.Trajet

interface TrajetDAO : DAO<Trajet> {
    /*- chercher tous les trajets d'un utilisateur.
-chercher un trajet d'un utilisateur.
- ajouter un trajet.
- supprimer un trajet.*/
    fun chercherTrajetsParutilisateur(id: Int): List<Trajet?>?
    fun chercherTrajetParUtilisateur(idTrajet: Int, idUtilisateur: Int): Trajet?
    override fun chercherTous(): List<Trajet>
    override fun chercherParId(id: Int): Trajet?
    override fun ajouter(t: Trajet): Trajet?

    override fun supprimer(id: Int): Boolean
}