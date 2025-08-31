package com.xotril.vendacar.domain.repository

import com.xotril.vendacar.domain.model.Vehicle

interface VehicleRepository {
    fun save(vehicle: Vehicle): Vehicle
    fun findById(id: Long): Vehicle?
    fun findAllAvailable(): List<Vehicle>
    fun findAllSold(): List<Vehicle>
}
