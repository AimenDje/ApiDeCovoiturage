package com.projet.covoiturage.Controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.projet.covoiturage.Exception.*
import com.projet.covoiturage.Model.Adresse
import com.projet.covoiturage.Model.Trajet
import com.projet.covoiturage.Model.Utilisateur
import com.projet.covoiturage.Service.TrajetService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*



@SpringBootTest
@AutoConfigureMockMvc
class TrajetControllerUtilisateurAuthentifiéTest {
    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: TrajetService

    @Autowired
    private lateinit var mockMvc: MockMvc



    @WithMockUser(username = "mahrez")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun `Étant donné Étant donnée un utilisateur authentifié ayant effectué deux trajets lorsqu'on effectue une requête GET de recherche des trajets par code alors on obtient un JSON qui contient 2 trajets dont le code est 1 et 2 et un code de retour 200`() {

        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val trajet = Trajet(1, null, null, null, utilisateur)
        val trajet2 = Trajet(2, null, null, null, utilisateur)
        val list: List<Trajet> = listOf(trajet, trajet2)
        Mockito.`when`(service.chercherUtilisateur(123)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherTrajetsParUtilisateur(123, "mahrez")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/123/trajets").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].trajetId").value("1"))
            .andExpect(jsonPath("$[1].trajetId").value("2"))
    }


    @WithMockUser("mahrez")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun `Étant donné l'utilisateur dont l'id est 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par l'id alors on obtient un code de retour 404 et le message d'erreur « L'utilisateur avec l'id 1 est introuvable »`() {
        val list: List<Trajet> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)
        Mockito.`when`(service.chercherTrajetsParUtilisateur(1, "mahrez")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/trajets").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                Assertions.assertEquals(
                    "L'utilisateur avec l'id 1 est introuvable.",
                    resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser("mahrez")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajets")
    fun `Étant donné l'utilisateur authentifié et qui n'a pas encore de trajet lorsqu'on effectue une requête GET de recherche par l'id utilisateur alors on obtient un code de retour 404 et le message d'erreur « l'utilisateur avec l'id 1 n'a effectué aucun trajet encore »`() {

        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val list: List<Trajet> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherTrajetsParUtilisateur(1, "mahrez")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/trajets").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is TrajetsIntrouvablesExc)
                Assertions.assertEquals(
                    "L'utilisateur avec l'id 1 n'a effectué aucun trajet pour le moment.",
                    resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser("mahrez")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun `Étant donné l'utilisateur authentifié et le trajet dont le code 1 et qui est inscrit au service et qui possède un trajet avec l'id 1  lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient 1 trajets dont le code est 1 et un code de retour 200`() {

        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val trajet = Trajet(1, "maison", null, null, utilisateur)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherTrajetParUtilisateur(1, 1, "mahrez")).thenReturn(trajet)

        mockMvc.perform(get("/utilisateur/1/trajet/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.trajetId").value("1"))
    }


    @WithMockUser
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun `Étant donné l'utilisateur dont le code est 1 et le trajet dont le code 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche d'un trajet par code utilisateur alors on obtient le message d'erreur « L'utilisateur avec l'id 1 est introuvable » et un code de retour 404`() {

        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)
        mockMvc.perform(get("/utilisateur/1/trajet/1").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                Assertions.assertEquals(
                    "L'utilisateur avec l'id 1 est introuvable.",
                    resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser(username = "mahrez")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/trajet/{idTrajet}")
    fun `Étant donné l'utilisateur dont l'id est 1 et qui est inscrit au service et qui n'a pas encore de trajet ou ne contient pas de trajet avec l'id 1 lorsqu'on effectue une requête GET de recherche par l'id alors on obtient un code de retour 404 et le message d'erreur « l'utilisateur avec l'id 1 n'a effectué aucun trajet encore»`() {
        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherTrajetParUtilisateur(1, 1, "mahrez")).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/trajet/1").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is TrajetIntrouvableExc)
                Assertions.assertEquals(
                    "Le trajet avec l'id 1 est introuvable pour l'utilisateur 1",
                    resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser
    @Test
    // @GetMapping("/trajet/id")
    fun `Étant donné un utilisateur authentifié et le trajet dont le code est 1  et qui est déjà inscrit au service lorsqu'on effectue une requête GET de recherche trajet par code alors on obtient un JSON qui contient un trajet dont le code est 1 et un code de retour 200`() {

        val trajet = Trajet(1, null, null, null, null)
        Mockito.`when`(service.chercherParId(1)).thenReturn(trajet)

        mockMvc.perform(get("/trajet/1").with(csrf()))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.trajetId").value("1"))
    }


    @WithMockUser
    @Test
    // @GetMapping("/trajet/id")
    fun `Étant donné un utilisateur authentifié le trajet dont le code est 2 et qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche d'un trajet par code alors on obtient un code de retour 404 avec un message d'erreur le trajet avec l'id 1 est introuvable`() {

        mockMvc.perform(get("/trajet/2").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is TrajetIntrouvableExc)
                Assertions.assertEquals(
                    "Le trajet avec l'id 2 est introuvable.",
                    resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser("mahrez")
    @Test
    // @PostMapping("/trajet")
    fun `Étant donné un utilisateur authentifié et le trajet dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un JSON qui contient un trajet dont le code est 1, un code de retour 200`() {

        val trajet = Trajet(1, "Montréal-Toronto", null, null, null)
        Mockito.`when`(service.ajouter(trajet, "mahrez")).thenReturn(trajet)

        mockMvc.perform(post("/trajet").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(trajet)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.trajetId").value("1"))
    }

    @WithMockUser
    @Test
    // @PostMapping("/trajet/{id}")
    fun `Étant donné un utilisateur authentifié et  le trajet dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter et que plusieurs champs sont inexistants alors on obtient un code de retour 400 et un message d'erreur "La trajet que vous essayez d'ajouter est incorrectement formulé ou est manquant"`() {

        val trajetStr = "{\"trajetId\": \"1\" }"

        mockMvc.perform(post("/trajet").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(trajetStr))
            .andExpect(status().isBadRequest)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is MauvaiseFormulationObjetExc)
                Assertions.assertEquals(
                    "La trajet que vous essayez d'ajouter est incorrectement formulé ou est manquant", resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser("mahrez")
    @Test
    //@PutMapping("/trajet/{id}")
    fun `Étant donné un utilisateur authentifié et le trajet dont l'id est 1 et qui est déjà inscrit au service lorsqu'on effectue une requête PUT pour modifier le nom du trajet on obtient un JSON qui contient un trajet dont le nom est travail, un code de retour 200`() {

        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val adresse = Adresse (21, "ddgahdg", "sdsd", "ddad", "gjhgk", "gkjg", "hkl")
        var trajet = Trajet(2, "école", adresse, adresse, utilisateur)
        var trajet2 = Trajet(2, "maison", adresse, adresse, utilisateur)
        Mockito.`when`(service.chercherParId(2)).thenReturn(trajet)
        Mockito.`when`(service.ajouter(trajet2, "mahrez")).thenReturn(trajet2)

        mockMvc.perform(put("/trajet/2").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(trajet2)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nom").value("maison"))
    }


    @WithMockUser
    @Test
    //@PutMapping("/trajet/{id}")
    fun `Étant donné un utilisateur authentifié le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête PUT et que le champ nom est manquant dans le JSON envoyé alors on obtient un code de retour 400 et le message d'erreur Le trajet avec l'id 8000 n'existe pas Utilisez une requête POST pour en ajouter un nouveau`() {

        val trajetStr = "{\"trajetId\": \"8000\", \"adresseDépart\": \"47847 Beaubien\"}"
        Mockito.`when`(service.chercherParId(8000)).thenReturn(null)

        mockMvc.perform(
            put("/trajet/8000").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(trajetStr)
        )
            .andExpect(status().isBadRequest)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is MauvaiseRequeteExc)
                Assertions.assertEquals(
                    "Le trajet avec l'id 8000 n'existe pas. Utilisez une requête POST pour en " +
                            "ajouter un nouveau.", resultat.resolvedException?.message
                )
            }
    }


    @WithMockUser("mahrez")
    @Test
    // @DeleteMapping("/trajet/{id}")
    fun `Étant donné un utilisateur authentifié et le trajet dont l'id est 123 et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 200 et le message "Le trajet avec l'id 123 a bien été supprimé"`() {

        val utilisateur = Utilisateur(1, "biden", "Djemaoune", "Aimen", "biden@biden.com", "4380000000", null, false)
        val trajet = Trajet(123, "école", null, null, utilisateur)
        Mockito.`when`(service.supprimer(123, "mahrez")).thenReturn(true)

        mockMvc.perform(delete("/trajet/123").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string("Le trajet avec l'id 123 a bien été supprimé."))
        }


    @WithMockUser("mahrez")
    @Test
    // @DeleteMapping("/trajet/{id}")
    fun `Étant donné un utilisateur authentifié et le trajet dont l'id est 8000 et qui n'est pas inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 404 et un message d'erreur "Le trajet avec l'id 8000 n'a pas pu être supprimé parce qu'il n'existe pas"`() {

        //val utilisateur = Utilisateur(1, "Djemaoune", "Aimen", "courriel", "4380000000", null, true)
        val trajet = Trajet(8000, null, null, null, null)
        Mockito.`when`(service.supprimer(8000, "mahrez")).thenReturn(false)

        mockMvc.perform(delete("/trajet/8000").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                Assertions.assertTrue(resultat.resolvedException is TrajetIntrouvableExc)
                Assertions.assertEquals("Le trajet avec l'id 8000 n'a pas pu être supprimé parce qu'il n'existe pas.",
                        resultat.resolvedException?.message)
            }
        }



    }