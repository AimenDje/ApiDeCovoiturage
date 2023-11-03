package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/")
    fun `Étant donné une requête GET à la racine alors on obtient un string et une code de retour 200`() {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(content().string("Service web du service de covoiturage"))
    }

}