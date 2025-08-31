package com.xotril.vendacar.web.response

import java.time.LocalDateTime

data class VehicleResponse(
    val id: Long,
    val brand: String,
    val model: String,
    val modelYear: Int,
    val color: String,
    val price: Double,
    val sold: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val sale: SaleResponse? = null
)
