package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.domain.model.Vehicle
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var vehicleFacade: VehicleFacade

    @Test
    fun `should list vehicles and return 200 OK`() {
        // GIVEN: Um veículo mockado que a Facade retornará
        val mockVehicle = Vehicle(
            id = 1L,
            brand = "Ford",
            model = "Mustang",
            modelYear = 2023,
            color = "Black",
            price = 350000.0
        )

        `when`(vehicleFacade.listVehicles(null, false)).thenReturn(listOf(mockVehicle))

        // WHEN & THEN: Fazemos a requisição e validamos o JSON de resposta
        mockMvc.perform(
            get("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].brand").value("Ford"))
            .andExpect(jsonPath("$[0].model").value("Mustang"))
    }

    @Test
    fun `should return 401 when trying to buy a vehicle without token`() {
        // Este teste garante que a segurança está ativa para a rota de compra
        mockMvc.perform(
            post("/vehicles/1/buy")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized)
    }
}
