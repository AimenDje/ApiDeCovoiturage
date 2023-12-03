package com.projet.covoiturage.Controllers

import com.projet.covoiturage.Service.ReservationService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

import com.projet.covoiturage.Model.Reservation
import com.projet.covoiturage.Model.Utilisateur
import com.fasterxml.jackson.databind.ObjectMapper
import com.projet.covoiturage.Exception.*
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf


@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTestAuth {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: ReservationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @WithMockUser(username = "biden")
    @Test
    // @GetMapping("/reservations")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 et la réservation dont le code est 2 lorsqu'on effectue une requête GET de recherche alors, on obtient un JSON qui contient deux réservations dont le code est 1 et 2 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(2, null, 99, null, null, false)
        val liste: List<Reservation> = listOf(reservation, reservation2)
        Mockito.`when`(service.chercherTous("biden")).thenReturn(liste)

        mockMvc.perform(get("/reservations").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("1"))
                .andExpect(jsonPath("$[1].reservationId").value("2"))
    }

    @WithMockUser
    @Test
    // @GetMapping("/reservations")
    // Exception
    fun `Étant donné un utilisateur authentifié et qu'il n'y a aucune réservation, lorsqu'on effectue une requête GET de recherche alors, on obtient un code de retour 404`() {
        mockMvc.perform(get("/reservations").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationsIntrouvablesExc)
                    assertEquals("Aucune réservation trouvée.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser
    @Test
    // @GetMapping("/reservation/{id}")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors, on obtient un JSON qui contient une réservation dont le code est 1 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, false)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)

        mockMvc.perform(get("/reservation/1").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @WithMockUser
    @Test
    // @GetMapping("/reservation/{id}")
    // Exception
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        mockMvc.perform(get("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation avec l'id 1 est introuvable.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser(username = "biden")
    @Test
    // @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 et un chauffeur dont le code est 1 lorsqu'on effectue une requête PUT par code de réservation et code d'utilisateur alors, on obtient un JSON qui contient une réservation acceptée dont le code est 1 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.accepterReservation(1, 1,"biden")).thenReturn(reservation)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)

        mockMvc.perform(put("/reservation/1/chauffeur/1/accept").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
                .andExpect(jsonPath("$.acceptee").value(true))
    }

    @WithMockUser(username = "biden")
    @Test
    // @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    // Exception
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 et un passager dont le code est 1 lorsqu'on effectue une requête PUT par code de réservation et code d'utilisateur alors, on obtient un code de retour 404`() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, true)
        Mockito.`when`(service.accepterReservation(1, 1, "biden")).thenReturn(reservation)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)

        mockMvc.perform(put("/reservation/1/chauffeur/1/accept").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is NonAuthoriseExc)
                    assertEquals("Cette fonctionnalité est réservée aux chauffeurs.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser(username = "biden")
    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Valide
    fun `Étant donné un utilisateur authentifié et le chauffeur dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors, on obtient un JSON qui contient une réservation acceptée par le chauffeur dont le code est 1 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationChaufeur(1, "biden")).thenReturn(reservation)

        mockMvc.perform(get("/chauffeur/1/reservation").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @WithMockUser
    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Exception
    fun `Étant donné un utilisateur authentifié et le chauffeur dont le code est 1 qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)

        mockMvc.perform(get("/chauffeur/1/reservation").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
            }
    }

    @WithMockUser(username = "biden")
    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Exception
    fun `Étant donné un utilisateur authentifié et le chauffeur dont le code est 1 qui n'a pas accepté de réservation lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationChaufeur(1, "biden")).thenReturn(null)

        mockMvc.perform(get("/chauffeur/1/reservation").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("Aucune réservation acceptée par ce chauffeur.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser("trudeau")
    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Valide
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 et les réservations dont le code est 1 et 2 lorsqu'on effectue une requête GET de recherche par code alors, on obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val reservation2 = Reservation(2, null, 99, null, null, true)
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        val list: List<Reservation> = listOf(reservation, reservation2)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1,"trudeau")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("1"))
                .andExpect(jsonPath("$[1].reservationId").value("2"))
    }

    @WithMockUser("trudeau")
    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Exception
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        val list: List<Reservation> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1, "trudeau")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                    assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser("trudeau")
    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Exception
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 qui ne possède aucune réservation lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        val list: List<Reservation> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1, "trudeau")).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is ReservationsIntrouvablesExc)
                assertEquals("L'utilisateur avec l'id 1 n'a effectué aucune réservation pour le moment.", resultat.resolvedException?.message)
            }
    }

    @WithMockUser("trudeau")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Valide
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 et la réservation dont le code 1 lorsqu'on effectue une requête GET de recherche par code utilisateur et pas code de réservation alors, on obtient un JSON qui une réservation dont le code est 1 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationParUtilisateur(1, 1, "trudeau")).thenReturn(reservation)

        mockMvc.perform(get("/utilisateur/1/reservation/1").with(csrf()))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @WithMockUser
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Exception
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche par code utilisateur et pas code de réservation alors, on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/reservation/1").with(csrf())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
            }
    }

    @WithMockUser(username = "trudeau")
    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Exception
    fun `Étant donné un utilisateur authentifié et le passager dont le code est 1 et la réservation dont le code est 1 qui n'existe pas dans le service lorsqu'on effectue une requête GET de recherche par code alors, on obtient un code de retour 404`() {
        val utilisateur = Utilisateur(1, "biden", "biden", "Biden", "biden@biden.com", "4380000000", null, false)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationParUtilisateur(1, 1, "trudeau")).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation avec l'id 1 est introuvable pour l'utilisateur 1", resultat.resolvedException?.message)
                }
    }

    @WithMockUser("trudeau")
    @Test
    // @PostMapping("/reservation")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour ajouter alors, on obtient un JSON qui contient une réservation dont le code est 1 et un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, false)
        Mockito.`when`(service.ajouter(reservation, "trudeau")).thenReturn(reservation)

        mockMvc.perform(post("/reservation").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @WithMockUser
    @Test
    // @PostMapping("/reservation")
    // Exception
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 qui possède plusieurs champs manquants et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter, on obtient un code de retour 400`() {
        val reservationStr = "{\"reservationId\": \"1\" }"

        mockMvc.perform(post("/reservation").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr))
                .andExpect(status().isBadRequest)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is MauvaiseFormulationObjetExc)
                    assertEquals("La réservation que vous essayez d'ajouter n'est pas formulée " +
                            "correctement.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser("trudeau")
    @Test
    // @PutMapping("/reservation/{id}")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 et le nombre de passagers est 66 qui est déjà inscrit au service lorsqu'on effectue une requête PUT pour modifier le nombre de passagers à 99 alors, on obtient un JSON qui contient une réservation dont le nombre de passagers est de 99, un code de retour 200`() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(1, null, 99, null, null, false)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.ajouter(reservation2, "trudeau")).thenReturn(reservation2)

        mockMvc.perform(put("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation2)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.nombrePassager").value("99"))
    }

    @WithMockUser
    @Test
    // @PutMapping("/reservation/{id}")
    // Exception
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 qui n'est pas inscrite au service lorsqu'on effectue une requête PUT pour modifier le nombre de passagers alors, on obtient un code de retour 400`() {
        val reservationStr = "{\"reservationId\": \"1\", \"nombrePassager\": \"66\"}"
        Mockito.`when`(service.chercherParId(1)).thenReturn(null)

        mockMvc.perform(put("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr))
                .andExpect(status().isBadRequest)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is MauvaiseRequeteExc)
                    assertEquals("La réservation avec l'id 1 n'existe pas. Utilisez une requête POST pour en " +
                            "ajouter une nouvelle.", resultat.resolvedException?.message)
                }
    }

    @WithMockUser("trudeau")
    @Test
    // @DeleteMapping("/reservation/{id}")
    // Valide
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 qui est inscrite au service lorsqu'on effectue une requête DELETE pour supprimer la réservation alors, on obtient code de retour 200`() {
        Mockito.`when`(service.supprimer(1, "trudeau")).thenReturn(true)

        mockMvc.perform(delete("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().string("La réservation avec l'id 1 a bien été supprimée."))
    }

    @WithMockUser("trudeau")
    @Test
    // @DeleteMapping("/reservation/{id}")
    // Exception
    fun `Étant donné un utilisateur authentifié et la réservation dont le code est 1 n'est pas inscrite au service lorsqu'on effectue une requête DELETE pour supprimer la réservation alors, on obtient un code de retour 404`() {
        Mockito.`when`(service.supprimer(1, "trudeau")).thenReturn(false)

        mockMvc.perform(delete("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation l'id 1 n'a pas pu être supprimée parce qu'elle " +
                            "n'existe pas.", resultat.resolvedException?.message)
                }
    }

}