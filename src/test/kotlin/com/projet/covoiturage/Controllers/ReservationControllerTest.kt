package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    //@MockBean
    //lateinit var service: ReservationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/reservation/{id}")
    fun `Étant donné la réservation dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on obtien un JSON qui contient une réservation dont le code est 1 et un code de retour 200`() {
        TODO("Méthode à implémenter")
    }

    @Test
    // @GetMapping("/reservation/{id}")
    fun `Étant donné la réservation dont le code est 1 qui n'existe pas lorsqu'on effectue un requête GET de recherche alors on obtient un code de retour 404`() {
        TODO("Méthode à implémenter")
    }

    @Test
    // @PostMapping("/reservation")
    fun `Étant donnée la réservation dont le code est 2 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un JSON qui contient une réservation dont le code est 2 et un code de retour 201`() {
        TODO("Méthode à implémenter")
    }

    @Test
    // @PostMapping("/reservation")
    fun `Étant donnée le restaurant dont le code est 2 qui existe déjà lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un code de retour 409`() {
        TODO("Méthode à implémenter")
    }

    @Test
    // @PostMapping("/reservation")
    fun `Étant donnée le restaurant dont le code est 3 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400`() {
        TODO("Méthode à implémenter")
    }

    @Test
    // @PutMapping("/reservation/{id}")
    fun `Étant donnée le restaurant dont le code est 2 et qui est inscrit au service et dont le nombre de passager est 1 lorsqu'on effectue une requête PUT pour modifier le nombre pour 2 alors on obtient un JSON qui contient une réservation dont le code est 2 et le nombre de passagers est 2 ainsi qu'un code de retour 200` (){
        TODO("Méthode à implémenter")
    }


    @Test
    // @PutMapping("/reservation/{id}")
    fun `Étant donnée la réservation dont le code est 3 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT alors on obtient un JSON qui contient une réservation dont le code est 3 et un code de retour 201` (){
        TODO("Méthode à implémenter")
    }

    @Test
    // @PutMapping("/reservation/{id}")
    fun `Étant donnée la réservation dont le code est 3 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT et que l'objet Trajet est manquant dans le JSON envoyé alors on obtient un code de retour 400` (){
        TODO("Méthode à implémenter")
    }

    @Test
    // @DeleteMapping("/reservation/{id}")
    fun `Étant donnée la réservation dont le code est 3 et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 204` (){
        TODO("Méthode à implémenter")
    }

    @Test
    // @DeleteMapping("/reservation/{id}")
    fun `Étant donné la réservation dont le code est 4 et qui n'est pas inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 404` (){
        TODO("Méthode à implémenter")
    }
}