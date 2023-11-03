package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class UtilisateurControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/utilisateurs")
    fun `Étant donné1`() {}

    @Test
    // @GetMapping("/utilisateur/{id}")
    fun `Étant donné2`() {}

    @Test
    // @PostMapping("/utilisateur")
    fun `Étant donné3`() {}

    @Test
    // @PutMapping("/utilisateur/{id}")
    fun `Étant donné4`() {}

    @Test
    // @DeleteMapping("/utilisateur/{id}")
    fun `Étant donné5`() {}

}