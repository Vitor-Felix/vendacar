package com.xotril.vendacar.web.request

data class VehicleRequest(
    val brand: String,
    val model: String,
    val modelYear: Int,
    val color: String,
    val price: Double
)
