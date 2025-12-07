package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.web.request.PaymentWebhookRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/webhook")
class PaymentWebhookController(private val vehicleFacade: VehicleFacade) {

    @Operation(
        summary = "Payment webhook",
        description = "Receives payment status updates from payment gateway. " +
                "Updates vehicle payment status based on external payment system notifications."
    )
    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    fun handlePayment(@RequestBody request: PaymentWebhookRequest) {
        vehicleFacade.updatePaymentStatus(request.paymentCode, request.status)
    }
}
