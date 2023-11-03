package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class AdresseControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/adresses")
    fun `Étant donné1`() {}

    @Test
    // @GetMapping("/adresse/{id}")
    fun `Étant donné2`() {}

    @Test
    // @PostMapping("/adresse")
    fun `Étant donné3`() {}

    @Test
    // @PutMapping("/adresse/{id}")
    fun `Étant donné4`() {}

    @Test
    // @DeleteMapping("/adresse/{id}")
    fun `Étant donné5`() {}

}