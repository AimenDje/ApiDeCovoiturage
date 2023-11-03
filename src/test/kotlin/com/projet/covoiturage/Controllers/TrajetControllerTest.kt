package com.projet.covoiturage.Controllers

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class TrajetControllerTest {

    //@MockBean
    //lateinit var service: TrajetService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    // @GetMapping("/trajets")
    fun `Étant donné`() {}

    @Test
    // @GetMapping("/trajet/{id}")
    fun `Étant donné2`() {}

    @Test
    // @PostMapping("/trajet")
    fun `Étant donné3`() {}

    @Test
    // @PutMapping("/trajet/{id}")
    fun `Étant donné4`() {}

    @Test
    // @DeleteMapping("/trajet/{id}")
    fun `Étant donné5`() {}

}