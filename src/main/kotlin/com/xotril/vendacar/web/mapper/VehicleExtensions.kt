package com.xotril.vendacar.web.mapper

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.web.request.VehicleRequest
import com.xotril.vendacar.web.response.SaleResponse
import com.xotril.vendacar.web.response.VehicleResponse

fun VehicleRequest.toDomain(): Vehicle =
    Vehicle(
        brand = this.brand,
        model = this.model,
        modelYear = this.modelYear,
        color = this.color,
        price = this.price
    )

fun Vehicle.toResponse(): VehicleResponse =
    VehicleResponse(
        id = this.id,
        brand = this.brand,
        model = this.model,
        modelYear = this.modelYear,
        color = this.color,
        price = this.price,
        sold = this.sold,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        sale = this.sale?.let {
            SaleResponse(
                buyerCpf = it.buyerCpf,
                saleDate = it.saleDate
            )
        }
    )
