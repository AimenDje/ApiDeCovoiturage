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


@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: ReservationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/reservations")
    // Pass
    /* Étant donné qu'on effectue une requête GET de recherche alors on obtient un JSON qui contient deux réservations
    dont le code est 1 et 2 et un code de retour 200
    */
    fun testReservations() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(2, null, 99, null, null, false)
        val liste: List<Reservation> = listOf(reservation, reservation2)
        Mockito.`when`(service.chercherTous()).thenReturn(liste)

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("1"))
                .andExpect(jsonPath("$[1].reservationId").value("2"))
    }

    @Test
    // @GetMapping("/reservations")
    // Exception
    /* Étant donné qu'on effectue une requête GET de recherche mais qu'il n'y a aucun réservations alors on obtient
     un code de retour 404
     */
    fun testReservationsExc() {
        mockMvc.perform(get("/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationsIntrouvablesExc)
                    assertEquals("Aucune réservation trouvée.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @GetMapping("/reservation/{id}")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on
     obtient un JSON qui contient une réservation dont le code est 1 et un code de retour 200
     */
    fun testReservationParId() {
        val reservation = Reservation(1, null, 66, null, null, false)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)

        mockMvc.perform(get("/reservation/1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @Test
    // @GetMapping("/reservation/{id}")
    // Exception
    /*
    Étant donné la réservation dont le code est 1 et qui n'existe pas dans le service lorsqu'on effectue une requête
    GET de recherche par code alors on obtient un code de retour 404
     */
    fun testReservationParIdExc() {
        mockMvc.perform(get("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation avec l'id 1 est introuvable.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 et l'utilisateur dont le code est 1 qui est un chauffeur lorsqu'on
    effectue une requête PUT par code de réservation et code d'utilisateur alors on obtient un JSON qui contient une
    réservation dont le code est 1 et un code de retour 200
     */
    fun testAccepterReservation() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, false)
        Mockito.`when`(service.accepterReservation(1, 1)).thenReturn(reservation)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)

        mockMvc.perform(put("/reservation/1/chauffeur/1/accept"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
                .andExpect(jsonPath("$.acceptee").value(true))
    }

    @Test
    // @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    // Exception
    /*
    Étant donné la réservation dont le code est 1 et l'utilisateur dont le code est 1 qui est un passager lorsqu'on
    effectue une requête PUT par code de réservation et code d'utilisateur alors on obtient un code de retour 404
     */
    fun testAccepterReservationExc() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        Mockito.`when`(service.accepterReservation(1, 1)).thenReturn(reservation)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)

        mockMvc.perform(put("/reservation/1/chauffeur/1/accept")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is NonAuthoriseExc)
                    assertEquals("Cette fonctionnalité est réservée aux chauffeurs.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Pass
    /*
    Étant donné le chauffeur dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on
    obtient un JSON qui contient une réservation acceptée par le chauffeur dont le code est 1 et un code de retour 200
     */
    fun testReservationAcceptee() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationChaufeur(1)).thenReturn(reservation)

        mockMvc.perform(get("/chauffeur/1/reservation"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Exception
            /*
            Étant donné le chauffeur dont le code est 1 qui n'a pas accepté de réservation lorsqu'on effectue une requête GET
            de recherche par code alors on obtient un code de retour 404
             */
    fun testReservationAccepteeExc() {
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)

        mockMvc.perform(get("/chauffeur/1/reservation")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
            }
    }

    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Exception
    /*
    Étant donné le chauffeur dont le code est 1 qui n'a pas accepté de réservation lorsqu'on effectue une requête GET
    de recherche par code alors on obtient un code de retour 404
     */
    fun testReservationAccepteeExc2() {
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationChaufeur(1)).thenReturn(null)

        mockMvc.perform(get("/chauffeur/1/reservation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("Aucune réservation acceptée par ce chauffeur.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Pass
    /*
    Étant donné le passager dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on
    obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et un code de retour 200
     */
    fun testReservationsUtilisateur() {
        val reservation = Reservation(1, null, 66, null, null, true)
        val reservation2 = Reservation(2, null, 99, null, null, true)
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        val list: List<Reservation> = listOf(reservation, reservation2)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1)).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("1"))
                .andExpect(jsonPath("$[1].reservationId").value("2"))
    }

    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Exception
    /*
    Étant donné le passager dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on
    obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et un code de retour 200
     */
    fun testReservationsUtilisateurExc() {
        val list: List<Reservation> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1)).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                    assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Exception
    /*
    Étant donné le passager dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors on
    obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et un code de retour 200
     */
    fun testReservationsUtilisateurExc2() {
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        val list: List<Reservation> = listOf()
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationsParUtilisateur(1)).thenReturn(list)

        mockMvc.perform(get("/utilisateur/1/reservations")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is ReservationsIntrouvablesExc)
                assertEquals("L'utilisateur avec l'id 1 n'a effectué aucune réservation pour le moment.", resultat.resolvedException?.message)
            }
    }

    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Pass
    /*
    Étant donné le passager dont le code est 1 et la réservation dont le code 1 lorsqu'on effectue une requête GET
    de recherche par code alors on obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et
    un code de retour 200
     */
    fun testReservationParUtilisateur() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationParUtilisateur(1, 1)).thenReturn(reservation)

        mockMvc.perform(get("/utilisateur/1/reservation/1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Exception
            /*
            Étant donné le passager dont le code est 1 et la réservation dont le code 1 qui n'existe pas lorsqu'on effectue
            une requête GET de recherche par code alors on obtient un code de retour 404
            */
    fun testReservationParUtilisateurExc() {
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/reservation/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { resultat ->
                assertTrue(resultat.resolvedException is UtilisateurIntrouvableExc)
                assertEquals("L'utilisateur avec l'id 1 est introuvable.", resultat.resolvedException?.message)
            }
    }

    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Exception
    /*
    Étant donné le passager dont le code est 1 et la réservation dont le code 1 qui n'existe pas lorsqu'on effectue
    une requête GET de recherche par code alors on obtient un code de retour 404
    */
    fun testReservationParUtilisateurExc2() {
        val utilisateur = Utilisateur(1, "ML", "Gabriel", "courriel", "4380000000", null, true)
        Mockito.`when`(service.chercherUtilisateur(1)).thenReturn(utilisateur)
        Mockito.`when`(service.chercherReservationParUtilisateur(1, 1)).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/reservation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation avec l'id 1 est introuvable pour l'utilisateur 1", resultat.resolvedException?.message)
                }
    }

    @Test
    // @PostMapping("/reservation")
    // Pass
    /*
    Étant donnée la réservation dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une
    requête POST pour l'ajouter alors on obtient un JSON qui contient une réservation dont le code est 1,
    un code de retour 200
     */
    fun testAjouterReservation() {
        val reservation = Reservation(1, null, 66, null, null, false)
        Mockito.`when`(service.ajouter(reservation)).thenReturn(reservation)

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.reservationId").value("1"))
    }

    @Test
    // @PostMapping("/reservation")
    // Exception
    /*
    Étant donnée la réservation dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une
    requête POST pour l'ajouter et que plusieurs champs sont inexistants alors on obtient un code de retour 400
     */
    fun testAjouterReservationExc() {
        val reservationStr = "{\"reservationId\": \"1\" }"

        mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr))
                .andExpect(status().isBadRequest)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is MauvaiseFormulationObjetExc)
                    assertEquals("La réservation que vous essayez d'ajouter est incorrectement " +
                            "formulée ou est manquante", resultat.resolvedException?.message)
                }
    }

    @Test
    // @PutMapping("/reservation/{id}")
    // Pass
    /*
    Étant donnée la réservation dont le code est 1 et le nombre de passager est 66 qui est déjà inscrit au service
    lorsqu'on effectue une requête PUT pour modifier le nombre de passager alors on obtient un JSON qui contient
    une réservation dont le nombre de passager est de 99, un code de retour 200
    */
    fun testModifierReservation() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(1, null, 99, null, null, false)
        Mockito.`when`(service.chercherParId(1)).thenReturn(reservation)
        Mockito.`when`(service.ajouter(reservation2)).thenReturn(reservation2)

        mockMvc.perform(put("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation2)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.nombrePassager").value("99"))
    }

    @Test
    // @PutMapping("/reservation/{id}")
    // Exception
    /*
    Étant donnée la réservation dont le code est 1 qui n'est pas inscrite au service lorsqu'on effectue une requête PUT
    pour modifier le nombre de passager alors on obtient un code de retour 400
    */
    fun testModifierReservationExc() {
        val reservationStr = "{\"reservationId\": \"1\", \"nombrePassager\": \"66\"}"
        Mockito.`when`(service.chercherParId(1)).thenReturn(null)

        mockMvc.perform(put("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationStr))
                .andExpect(status().isBadRequest)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is MauvaiseRequeteExc)
                    assertEquals("La réservation avec l'id 1 n'existe pas. Utilisez une requête POST pour en " +
                            "ajouter une nouvelle.", resultat.resolvedException?.message)
                }
    }

    @Test
    // @DeleteMapping("/reservation/{id}")
    // Pass
    /*
    Étant la réservation dont le code est 1 qui est inscrite au service lorsqu'on effectue une requête DELETE pour
    supprimer la réservation alors la réservation est supprimée et un code de retour 200
    */
    fun testSupprimerReservation() {
        Mockito.`when`(service.supprimer(1)).thenReturn(true)

        mockMvc.perform(delete("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON))
                //.content(mapper.writeValueAsString(reservation2)))
                .andExpect(status().isOk)
                .andExpect(content().string("La réservation avec l'id 1 a bien été supprimée."))
    }

    @Test
    // @DeleteMapping("/reservation/{id}")
    // Exception
    /*
    Étant la réservation dont le code est 1 n'est pas inscrite au service lorsqu'on effectue une requête DELETE pour
    supprimer la réservation alors un code de retour 404 est retourné
    */
    fun testSupprimerReservationExc() {
        Mockito.`when`(service.supprimer(1)).thenReturn(false)

        mockMvc.perform(delete("/reservation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { resultat ->
                    assertTrue(resultat.resolvedException is ReservationIntrouvableExc)
                    assertEquals("La réservation l'id 1 n'a pas pu être supprimée parce qu'elle " +
                            "n'existe pas.", resultat.resolvedException?.message)
                }
    }

}