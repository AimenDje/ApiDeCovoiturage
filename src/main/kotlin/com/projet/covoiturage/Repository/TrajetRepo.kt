package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Trajet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TrajetRepo : JpaRepository<Trajet, Int> {

    //chercher tous les trajets d'un utilisateur spécifique
    @Query("select t from Trajet  t where t.utilisateur.utilisateurId = :utilisateur_id")
    fun findAllTrajetsByUser(@Param("utilisateur_id") id: Int): List<Trajet?>?

    // chercher un trajet d'un utilisateur spécificque
    @Query("select t from Trajet  t where t.utilisateur.utilisateurId = :utilisateur_id and t.trajetId = :trajet_id")
    fun findTrajetByUser(
        @Param("trajet_id") idTrajet: Int,
        @Param("utilisateur_id") idUtilisateur: Int): Trajet?
}