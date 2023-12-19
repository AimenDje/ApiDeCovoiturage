package com.projet.covoiturage.Controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur
import com.projet.covoiturage.Service.TrajetService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
class TrajetControllerUtilisateurNonAuthentifiéTests {
    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: TrajetService

    @Autowired
    private lateinit var mockMvc: MockMvc



    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun `Étant donné un utilisateur non-authentifié et le passager dont le code est 1 lorsqu'on effectue une requête GET de recherche de trajets par code utilisateur alors, on obtient un code de retour 401`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/utilisateur/1/trajet").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idTrajet}")
    fun `Étant donné un utilisateur non-authentifié et le passager dont le code est 1 et le trajet dont le code 1 lorsqu'on effectue une requête GET de recherche d'un trajet par code utilisateur alors, on obtient un code de retour 401`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/utilisateur/1/trajet/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


    @Test
    // @GetMapping("/trajet/{id}")
    fun `Étant donné un utilisateur non-authentifié et le trajet dont le code est 1 lorsqu'on effectue une requête GET de recherche de trajet par code alors, on obtient un code de retour 401`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/trajet/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


    @Test
    // @PostMapping("/trajet")
    fun `Étant donné un utilisateur non-authentifié et le trajet dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors, on obtient un code de retour 401`() {
        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val trajet = Trajet(1, null, null, null, utilisateur)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/trajet").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(trajet)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


    @Test
    // @PutMapping("/reservation/{id}")
    fun `Étant donné un utilisateur non-authentifié et le trajet dont le code est 2 où le nom est école qui est déjà inscrit au service lorsqu'on effectue une requête PUT pour modifier le nom alors, on obtient un code de retour 401`() {
        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val adresse = Adresse (21, "ddgahdg", "sdsd", "ddad", "gjhgk", "gkjg", "hkl")
        var trajet = Trajet(2, "école", adresse, adresse, utilisateur)
        var trajet2 = Trajet(2, "maison", adresse, adresse, utilisateur)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/trajet/2").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(trajet2)))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


    @Test
    // @DeleteMapping("/reservation/{id}")
    fun `Étant donné un utilisateur non-authentifié et le trajet dont le code est 1 qui est inscrite au service, lorsqu'on effectue une requête DELETE pour supprimer le trajet alors, on obtient un code de retour 401`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/trajet/1").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }


}