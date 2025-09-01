package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.web.request.VehicleRequest
import com.xotril.vendacar.web.response.VehicleResponse
import com.xotril.vendacar.web.mapper.toDomain
import com.xotril.vendacar.web.mapper.toResponse
import com.xotril.vendacar.web.request.SaleRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(private val vehicleFacade: VehicleFacade) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVehicle(@RequestBody request: VehicleRequest): VehicleResponse {
        val vehicle = vehicleFacade.createVehicle(request.toDomain())
        return vehicle.toResponse()
    }

    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Long): VehicleResponse? =
        vehicleFacade.findVehicleById(id)?.toResponse()

    @GetMapping
    fun getVehicles(
        @RequestParam(required = false) sold: Boolean?,
        @RequestParam(required = false, defaultValue = "false") orderByPrice: Boolean
    ): List<VehicleResponse> {
        return vehicleFacade.listVehicles(sold, orderByPrice).map { it.toResponse() }
    }

    @PutMapping("/{id}")
    fun updateVehicle(
        @PathVariable id: Long,
        @RequestBody request: VehicleRequest
    ): VehicleResponse {
        val vehicle = vehicleFacade.updateVehicle(id, request)
        return vehicle.toResponse()
    }

    @PostMapping("/{id}/sell")
    fun sellVehicle(
        @PathVariable id: Long,
        @RequestBody request: SaleRequest
    ): VehicleResponse {
        val vehicle = vehicleFacade.sellVehicle(id, request)
        return vehicle.toResponse()
    }
}
