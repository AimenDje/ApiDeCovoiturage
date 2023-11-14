package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepo : JpaRepository<Reservation, Int> {
    //@Query("SELECT * FROM Reservation WHERE :#{#customer.firstname} ")
    @Query("select r from Reservation r where r.trajet.trajetId = any " +
            "(select t.trajetId from Trajet t where t.utilisateur.utilisateurId = :utilisateur_id)")
    fun findAllReservationByUser(@Param("utilisateur_id") id: Int): List<Reservation?>?

    @Query("select r from Reservation r where r.reservationId = :reservation_id and r.trajet.trajetId = any " +
            "(select t.trajetId from Trajet t where t.utilisateur.utilisateurId = :utilisateur_id)")
    fun findReservationByUserAndReservation(
            @Param("reservation_id") idReservation: Int,
            @Param("utilisateur_id") idUtilisateur: Int): Reservation?
}