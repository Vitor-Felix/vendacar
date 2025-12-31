package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PaymentWebhookControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var vehicleFacade: VehicleFacade

    @Test
    fun `should receive payment notification and return 200 OK`() {
        val json = """
            {
                "paymentCode": "PAY-123",
                "status": "PAID"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/webhook/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk)

        // Verifica se o controller chamou a facade corretamente
        verify(vehicleFacade).updatePaymentStatus("PAY-123", "PAID")
    }
}
