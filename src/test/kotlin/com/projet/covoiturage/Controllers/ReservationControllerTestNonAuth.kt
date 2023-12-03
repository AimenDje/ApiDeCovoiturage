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
class ReservationControllerTestNonAuth {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockBean
    lateinit var service: ReservationService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/reservations")
    // Pass
    /* Étant donné un utilisateur non-authentifié et la réservation dont le code est 1 et la réservation dont le code est 2
    lorsqu'on effectue une requête GET de recherche alors, on obtient un JSON qui contient deux réservations
    dont le code est 1 et 2 et un code de retour 200
    */
    fun testReservations() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(2, null, 99, null, null, false)
        val liste: List<Reservation> = listOf(reservation, reservation2)
        Mockito.`when`(service.chercherTous("biden")).thenReturn(liste)

        mockMvc.perform(get("/reservations").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @GetMapping("/reservation/{id}")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors, on
     obtient un JSON qui contient une réservation dont le code est 1 et un code de retour 200
     */
    fun testReservationParId() {
        mockMvc.perform(get("/reservation/1").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @PutMapping("/reservation/{idReservation}/chauffeur/{idChauffeur}/accept")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 et l'utilisateur dont le code est 1 qui est un chauffeur lorsqu'on
    effectue une requête PUT par code de réservation et code d'utilisateur alors, on obtient un JSON qui contient une
    réservation dont le code est 1 et un code de retour 200
     */
    fun testAccepterReservation() {
        mockMvc.perform(put("/reservation/1/chauffeur/1/accept").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @GetMapping("/chauffeur/{id}/reservation")
    // Pass
    /*
    Étant donné le chauffeur dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors, on
    obtient un JSON qui contient une réservation acceptée par le chauffeur dont le code est 1 et un code de retour 200
     */
    fun testReservationAcceptee() {
        mockMvc.perform(get("/chauffeur/1/reservation").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @GetMapping("/utilisateur/{id}/reservations")
    // Pass
    /*
    Étant donné le passager dont le code est 1 lorsqu'on effectue une requête GET de recherche par code alors, on
    obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et un code de retour 200
     */
    fun testReservationsUtilisateur() {
        mockMvc.perform(get("/utilisateur/1/reservations").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @GetMapping("/utilisateur/{idUtilisateur}/reservation/{idReservation}")
    // Pass
    /*
    Étant donné le passager dont le code est 1 et la réservation dont le code 1 lorsqu'on effectue une requête GET
    de recherche par code alors, on obtient un JSON qui contient 2 réservations dont le code est 1 et 2 et
    un code de retour 200
     */
    fun testReservationParUtilisateur() {
        mockMvc.perform(get("/utilisateur/1/reservation/1").with(csrf()))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @PostMapping("/reservation")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 et qui n'est pas inscrit au service lorsqu'on effectue une
    requête POST pour l'ajouter alors, on obtient un JSON qui contient une réservation dont le code est 1,
    un code de retour 200
     */
    fun testAjouterReservation() {
        val reservation = Reservation(1, null, 66, null, null, false)

        mockMvc.perform(post("/reservation").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(reservation)))
            .andExpect(status().isUnauthorized)
    }

    @Test
    // @PutMapping("/reservation/{id}")
    // Pass
    /*
    Étant donné la réservation dont le code est 1 et le nombre de passagers est 66 qui est déjà inscrit au service
    lorsqu'on effectue une requête PUT pour modifier le nombre de passagers alors, on obtient un JSON qui contient
    une réservation dont le nombre de passagers est de 99, un code de retour 200
    */
    fun testModifierReservation() {
        val reservation = Reservation(1, null, 66, null, null, false)
        val reservation2 = Reservation(1, null, 99, null, null, false)

        mockMvc.perform(put("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reservation2)))
                .andExpect(status().isUnauthorized)
    }

    @Test
    // @DeleteMapping("/reservation/{id}")
    // Pass
    /*
    Étant la réservation dont le code est 1 qui est inscrite au service lorsqu'on effectue une requête DELETE pour
    supprimer la réservation alors la réservation est supprimée et un code de retour 200
    */
    fun testSupprimerReservation() {
        mockMvc.perform(delete("/reservation/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized)
    }
}