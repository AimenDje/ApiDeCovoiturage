package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class TrajetControllerTest {

    //@MockBean
    //lateinit var service: TrajetService

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    // @GetMapping("/trajet/{id}")
    fun `Étant donné le trajet dont l'id est 123 lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient un trajet dont l"id est 123 et un code de retour 200`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    // @GetMapping("/trajet/{id}")
    fun `Étant donné le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par l'id alors on obtient un code de retour 404 et le message d'erreur « Le trajet 8000 n'est pas inscrit au service »`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    // @PostMapping("/trajet")
    fun `Étant donnée le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un JSON qui contient un trajet dont l'id est 8000 et un code de retour 201`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    // @PostMapping("/trajet")
    fun `Étant donnée le trajet dont l'id est 123 qui existe déjà lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un code de retour 409`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    // @PostMapping("/trajet")
    fun `Étant donnée le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400`() {
        TODO("Méthode non-implémentée")
    }


    @Test
    // @PutMapping("/trajet/{id}")
    fun `Étant donnée le trajet dont l'id est 123 et qui est inscrit au service et dont le nom est Mtl-Tor lorsqu'on effectue une requête PUT pour modifier le nom pour « Dorval-Toronto » alors on obtient un JSON qui contient un trajet dont l'id est 123 et le nom est « Dorval-Toronto » ainsi qu'un code de retour 200`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    //@PutMapping("/trajet/{id}")
    fun `Étant donnée le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT alors on obtient un JSON qui contient un trajet dont l'id est 8000 et un code de retour 201`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    //@PutMapping("/trajet/{id}")
    fun `Étant donnée le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    // @DeleteMapping("/trajet/{id}")
    fun `Étant donnée le trajet dont l'id est 123 et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 204`() {
        TODO("Méthode non-implémentée")
    }
    @Test
    // @DeleteMapping("/trajet/{id}")
    fun `Étant donné le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 404`() {
        TODO("Méthode non-implémentée")
    }

}