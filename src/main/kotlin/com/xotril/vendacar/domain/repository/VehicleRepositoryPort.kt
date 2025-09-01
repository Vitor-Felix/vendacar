package com.xotril.vendacar.domain.repository

import com.xotril.vendacar.domain.model.Vehicle

interface VehicleRepositoryPort {
    fun save(vehicle: Vehicle): Vehicle
    fun findById(id: Long): Vehicle?
    fun findBySold(sold: Boolean? = null, orderByPrice: Boolean = false): List<Vehicle>
}
