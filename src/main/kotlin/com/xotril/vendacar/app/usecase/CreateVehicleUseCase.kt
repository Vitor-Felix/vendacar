package com.xotril.vendacar.app.usecase

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort

class CreateVehicleUseCase(private val vehicleRepositoryPort: VehicleRepositoryPort) {
    fun execute(vehicle: Vehicle): Vehicle {
        return vehicleRepositoryPort.save(vehicle)
    }
}
