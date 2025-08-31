package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.service.VehicleService
import com.xotril.vendacar.web.request.VehicleRequest
import com.xotril.vendacar.web.response.VehicleResponse
import com.xotril.vendacar.web.mapper.toDomain
import com.xotril.vendacar.web.mapper.toResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(private val vehicleService: VehicleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVehicle(@RequestBody request: VehicleRequest): VehicleResponse {
        val vehicle = vehicleService.createVehicle(request.toDomain())
        return vehicle.toResponse()
    }

    @GetMapping("/available")
    fun getAvailableVehicles(): List<VehicleResponse> =
        vehicleService.listAvailableVehicles().map { it.toResponse() }

    @GetMapping("/available/sorted")
    fun getAvailableVehiclesSortedByPrice(): List<VehicleResponse> = // 👈 novo endpoint
        vehicleService.listAvailableVehiclesSortedByPrice().map { it.toResponse() }

    @GetMapping("/sold")
    fun getSoldVehicles(): List<VehicleResponse> =
        vehicleService.listSoldVehicles().map { it.toResponse() }

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Long): VehicleResponse? =
        vehicleService.findVehicleById(id)?.toResponse()
}
