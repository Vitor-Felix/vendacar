package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.web.request.PaymentWebhookRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/webhook")
class PaymentWebhookController(private val vehicleFacade: VehicleFacade) {

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.OK)
    fun handlePayment(@RequestBody request: PaymentWebhookRequest) {
        vehicleFacade.updatePaymentStatus(request.paymentCode, request.status)
    }
}
