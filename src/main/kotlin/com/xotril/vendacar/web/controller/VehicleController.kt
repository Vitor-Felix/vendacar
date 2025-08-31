package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.service.VehicleService
import com.xotril.vendacar.domain.model.Vehicle
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(private val vehicleService: VehicleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVehicle(@RequestBody vehicle: Vehicle): Vehicle {
        return vehicleService.createVehicle(vehicle)
    }

    @GetMapping("/available")
    fun getAvailableVehicles(): List<Vehicle> {
        return vehicleService.listAvailableVehicles()
    }

    @GetMapping("/sold")
    fun getSoldVehicles(): List<Vehicle> {
        return vehicleService.listSoldVehicles()
    }

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Long): Vehicle? {
        return vehicleService.findVehicleById(id)
    }
}
