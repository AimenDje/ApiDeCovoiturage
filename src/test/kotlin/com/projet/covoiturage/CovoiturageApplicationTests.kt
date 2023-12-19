package com.projet.covoiturage

import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur
import com.projet.covoiturage.Repository.AdresseRepo
import com.projet.covoiturage.Repository.ReservationRepo
import com.projet.covoiturage.Repository.TrajetRepo
import com.projet.covoiturage.Repository.UtilisateurRepo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CovoiturageApplicationTests(
	@Autowired
	val trajetRepo: TrajetRepo,
	@Autowired
	val adresseRepo: AdresseRepo,
	@Autowired
	val reservationRepo: ReservationRepo,
	@Autowired
	val utilisateurRepo: UtilisateurRepo) {

	@Test
	fun testAjouterTrajet() {
		//val adresseDepart: Adresse = Adresse(null, "1111", "app", "rue", "ville", "prov", "postal")
		//val adresseArrivee: Adresse = Adresse(null, "2222", "app", "rue", "ville", "prov", "postal")
		//adresseRepo.save(adresseDepart)
		//adresseRepo.save(adresseArrivee)
		val adresseDepart: Adresse = adresseRepo.findById(1).get()
		val adresseArrivee: Adresse = adresseRepo.findById(2).get()
		val utilisateur: Utilisateur = utilisateurRepo.findById(2).get()

		val trajet: Trajet = Trajet( null, "Trajet test", adresseDepart, adresseArrivee, utilisateur)
		trajetRepo.save(trajet)
	}

}
