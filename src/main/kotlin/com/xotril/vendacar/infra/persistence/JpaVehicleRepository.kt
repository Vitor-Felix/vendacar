package com.xotril.vendacar.infra.persistence

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface SpringDataVehicleRepository : JpaRepository<Vehicle, Long> {
    fun findAllBySoldFalseOrderByPriceAsc(): List<Vehicle>
    fun findAllBySoldTrueOrderByPriceAsc(): List<Vehicle>
}

@Repository
class JpaVehicleRepository(
    private val springDataVehicleRepository: SpringDataVehicleRepository
) : VehicleRepository {

    override fun save(vehicle: Vehicle): Vehicle {
        return springDataVehicleRepository.save(vehicle)
    }

    override fun findById(id: Long): Vehicle? {
        return springDataVehicleRepository.findById(id).orElse(null)
    }

    override fun findAllAvailable(): List<Vehicle> {
        return springDataVehicleRepository.findAllBySoldFalseOrderByPriceAsc()
    }

    override fun findAllSold(): List<Vehicle> {
        return springDataVehicleRepository.findAllBySoldTrueOrderByPriceAsc()
    }
}
