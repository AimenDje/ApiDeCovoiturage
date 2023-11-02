package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.bind.annotation.PostMapping

@SpringBootTest
@AutoConfigureMockMvc
class ApiController {

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