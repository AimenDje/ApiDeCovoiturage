package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepo : JpaRepository<Reservation, Int> {
    //@Query("SELECT * FROM Reservation WHERE :#{#customer.firstname} ")
    @Query("select * from reservation where trajet_id = any (select trajet_id from trajet where utilisateur_id = :#{#utilisateur_id})")
    fun findAllReservationByUser(@Param("utilisateur_id") id : Int ): List<Reservation?>?

}