package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepo : JpaRepository<Reservation, Int> {}