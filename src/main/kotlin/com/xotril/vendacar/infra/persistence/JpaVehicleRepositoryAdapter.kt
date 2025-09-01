package com.xotril.vendacar.infra.persistence

import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SpringDataVehicleRepository : JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.sold = false")
    fun findAllAvailable(): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.sold = false ORDER BY v.price ASC")
    fun findAllAvailableOrderByPriceAsc(): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.sold = true")
    fun findAllSold(): List<Vehicle>

    @Query("SELECT v FROM Vehicle v WHERE v.sold = true ORDER BY v.price ASC")
    fun findAllSoldOrderByPriceAsc(): List<Vehicle>
}

@Repository
class JpaVehicleRepositoryAdapter(private val springRepo: SpringDataVehicleRepository) : VehicleRepositoryPort {

    override fun save(vehicle: Vehicle): Vehicle = springRepo.save(vehicle)

    override fun findById(id: Long): Vehicle? = springRepo.findById(id).orElse(null)

    override fun findBySold(sold: Boolean?, orderByPrice: Boolean): List<Vehicle> {
        return when {
            sold == null && !orderByPrice -> springRepo.findAllAvailable()
            sold == null && orderByPrice -> springRepo.findAllAvailableOrderByPriceAsc()
            sold == true && !orderByPrice -> springRepo.findAllSold()
            sold == true && orderByPrice -> springRepo.findAllSoldOrderByPriceAsc()
            sold == false && !orderByPrice -> springRepo.findAllAvailable()
            sold == false && orderByPrice -> springRepo.findAllAvailableOrderByPriceAsc()
            else -> springRepo.findAllAvailable()
        }
    }
}
