package com.xotril.vendacar.web.request

data class PaymentWebhookRequest(
    val paymentCode: String,
    val status: String
)
