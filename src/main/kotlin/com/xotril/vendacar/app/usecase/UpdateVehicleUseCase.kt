package com.xotril.vendacar.app.usecase

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort
import com.xotril.vendacar.web.request.VehicleRequest

class UpdateVehicleUseCase(private val vehicleRepositoryPort: VehicleRepositoryPort) {
    fun execute(vehicleId: Long, request: VehicleRequest): Vehicle {
        val vehicle = vehicleRepositoryPort.findById(vehicleId)
            ?: throw IllegalArgumentException("Vehicle with id $vehicleId not found")

        vehicle.brand = request.brand
        vehicle.model = request.model
        vehicle.modelYear = request.modelYear
        vehicle.color = request.color
        vehicle.price = request.price

        return vehicleRepositoryPort.save(vehicle)
    }
}
