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
    // @GetMapping("/reservations")
    fun `Étant donné`() {}

    @Test
    // @GetMapping("/reservation/{id}")
    fun `Étant donné2`() {}

    @Test
    // @GetMapping("/trajet/{id}/reservations")
    fun `Étant donné3`() {}

    @Test
    // @PostMapping("/reservation")
    fun `Étant donné4`() {}

    @Test
    // @PutMapping("/reservation/{id}")
    fun `Étant donné5`() {}

    @Test
    // @DeleteMapping("/reservation/{id}")
    fun `Étant donné6`() {}
}