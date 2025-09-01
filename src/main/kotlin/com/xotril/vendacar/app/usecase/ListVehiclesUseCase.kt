package com.xotril.vendacar.app.usecase

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort

class ListVehiclesUseCase(private val vehicleRepositoryPort: VehicleRepositoryPort) {
    fun execute(sold: Boolean?, orderByPrice: Boolean): List<Vehicle> {
        return vehicleRepositoryPort.findBySold(sold, orderByPrice)
    }
}
