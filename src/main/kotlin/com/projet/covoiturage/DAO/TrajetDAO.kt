package com.projet.covoiturage.DAO


import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur

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
    fun validerTrajet(trajet: Trajet, userId: String): Boolean
    fun validerPassager(userId: String): Boolean
    fun validerPassagerEtId(id: Int, userId: String): Boolean
    fun validerSuppression(idTrajet: Int, userId: String): Boolean
    fun chercherUtilisateur(id: Int): Utilisateur?
}