package com.projet.covoiturage.Repository

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Utilisateur
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UtilisateurRepo : JpaRepository<Utilisateur, Int>