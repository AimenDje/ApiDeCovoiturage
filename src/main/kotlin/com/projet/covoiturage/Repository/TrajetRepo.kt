package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Trajet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrajetRepo : JpaRepository<Trajet, Int> {}