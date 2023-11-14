package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdresseRepo : JpaRepository<Adresse, Int> {}