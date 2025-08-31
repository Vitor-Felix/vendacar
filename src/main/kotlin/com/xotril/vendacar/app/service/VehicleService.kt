package com.xotril.vendacar.app.service

import com.xotril.vendacar.domain.model.Sale
import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.SaleRepository
import com.xotril.vendacar.domain.repository.VehicleRepository
import com.xotril.vendacar.web.request.SaleRequest
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VehicleService(
    private val vehicleRepository: VehicleRepository,
    private val saleRepository: SaleRepository
) {

    fun createVehicle(vehicle: Vehicle): Vehicle {
        return vehicleRepository.save(vehicle)
    }

    fun findVehicleById(id: Long): Vehicle? {
        return vehicleRepository.findById(id)
    }

    fun listVehicles(sold: Boolean?, orderByPrice: Boolean): List<Vehicle> {
        return vehicleRepository.findBySold(sold, orderByPrice)
    }

    fun sellVehicle(vehicleId: Long, request: SaleRequest): Vehicle {
        val vehicle = vehicleRepository.findById(vehicleId)
            ?: throw IllegalArgumentException("Vehicle with id $vehicleId not found")

        if (vehicle.sold) {
            throw IllegalStateException("Vehicle with id $vehicleId is already sold")
        }

        val sale = Sale(
            buyerCpf = request.buyerCpf,
            saleDate = LocalDateTime.now(),
            vehicle = vehicle
        )

        vehicle.sale = sale
        vehicle.sold = true

        saleRepository.save(sale)
        return vehicleRepository.save(vehicle)
    }
}
