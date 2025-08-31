package com.xotril.vendacar.infra.persistence

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SpringDataVehicleRepository : JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.sold = false ORDER BY v.price ASC")
    fun findAllAvailableOrderByPriceAsc(): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.sold = false")
    fun findAllAvailable(): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.sold = true")
    fun findAllSold(): List<Vehicle>
}

@Repository
class JpaVehicleRepository(private val springRepo: SpringDataVehicleRepository) : VehicleRepository {

    override fun save(vehicle: Vehicle): Vehicle = springRepo.save(vehicle)

    override fun findById(id: Long): Vehicle? = springRepo.findById(id).orElse(null)

    override fun findAllAvailable(): List<Vehicle> = springRepo.findAllAvailable()

    override fun findAllSold(): List<Vehicle> = springRepo.findAllSold()

    override fun findAllAvailableOrderByPriceAsc(): List<Vehicle> =
        springRepo.findAllAvailableOrderByPriceAsc()
}
