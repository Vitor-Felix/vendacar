package com.xotril.vendacar.app.usecase

import com.xotril.vendacar.domain.model.Sale
import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.SaleRepositoryPort
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort
import java.time.LocalDateTime

class SellVehicleUseCase(
    private val vehicleRepositoryPort: VehicleRepositoryPort,
    private val saleRepositoryPort: SaleRepositoryPort
) {
    fun execute(vehicleId: Long, buyerCpf: String): Vehicle {
        val vehicle = vehicleRepositoryPort.findById(vehicleId)
            ?: throw IllegalArgumentException("Vehicle with id $vehicleId not found")
        if (vehicle.sold) throw IllegalStateException("Vehicle already sold")

        val sale = Sale(buyerCpf = buyerCpf, saleDate = LocalDateTime.now(), vehicle = vehicle)
        vehicle.sale = sale
        vehicle.sold = true

        saleRepositoryPort.save(sale)
        return vehicleRepositoryPort.save(vehicle)
    }
}
