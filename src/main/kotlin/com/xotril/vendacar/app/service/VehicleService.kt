package com.xotril.vendacar.app.service

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class VehicleService(private val vehicleRepository: VehicleRepository) {

    fun createVehicle(vehicle: Vehicle): Vehicle {
        return vehicleRepository.save(vehicle)
    }

    fun listAvailableVehicles(): List<Vehicle> {
        return vehicleRepository.findAllAvailable()
    }

    fun listSoldVehicles(): List<Vehicle> {
        return vehicleRepository.findAllSold()
    }

    fun findVehicleById(id: Long): Vehicle? {
        return vehicleRepository.findById(id)
    }

    fun listAvailableVehiclesSortedByPrice(): List<Vehicle> { // 👈 novo caso de uso
        return vehicleRepository.findAllAvailableOrderByPriceAsc()
    }
}
