package com.xotril.vendacar.web.response

import java.time.LocalDateTime

data class SaleResponse(
    val buyerCpf: String,
    val saleDate: LocalDateTime,
    val code: String,
)
