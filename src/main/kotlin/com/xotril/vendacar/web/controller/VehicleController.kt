package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.web.request.VehicleRequest
import com.xotril.vendacar.web.response.VehicleResponse
import com.xotril.vendacar.web.mapper.toDomain
import com.xotril.vendacar.web.mapper.toResponse
import com.xotril.vendacar.web.request.SaleRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(private val vehicleFacade: VehicleFacade) {

    @Operation(
        summary = "Register a vehicle for sale",
        description = "Creates a new vehicle listing with brand, model, year, color, and price details"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVehicle(@RequestBody request: VehicleRequest): VehicleResponse {
        val vehicle = vehicleFacade.createVehicle(request.toDomain())
        return vehicle.toResponse()
    }

    @Operation(
        summary = "Find vehicle by ID",
        description = "Returns a single vehicle's complete details"
    )
    @GetMapping("/{id}")
    fun getVehicleById(@PathVariable id: Long): VehicleResponse? =
        vehicleFacade.findVehicleById(id)?.toResponse()

    @Operation(
        summary = "List vehicles",
        description = "Retrieves vehicles with filtering options. " +
                "When 'sold' parameter is: " +
                "- true: returns sold vehicles " +
                "- false: returns vehicles for sale. " +
                "Results can be sorted by price (lowest to highest) using 'orderByPrice' parameter."
    )
    @GetMapping
    fun getVehicles(
        @RequestParam(required = false) sold: Boolean?,
        @RequestParam(required = false, defaultValue = "false") orderByPrice: Boolean
    ): List<VehicleResponse> {
        return vehicleFacade.listVehicles(sold, orderByPrice).map { it.toResponse() }
    }

    @Operation(
        summary = "Edit vehicle data",
        description = "Updates the information of an existing vehicle in the system"
    )
    @PutMapping("/{id}")
    fun updateVehicle(
        @PathVariable id: Long,
        @RequestBody request: VehicleRequest
    ): VehicleResponse {
        val vehicle = vehicleFacade.updateVehicle(id, request)
        return vehicle.toResponse()
    }

    @Operation(
        summary = "Sell a vehicle",
        description = "Marks a specific vehicle as sold with sale details (date, price, buyer info)"
    )
    @PostMapping("/{id}/sell")
    fun sellVehicle(
        @PathVariable id: Long,
        @RequestBody request: SaleRequest
    ): VehicleResponse {
        val vehicle = vehicleFacade.sellVehicle(id, request)
        return vehicle.toResponse()
    }
}
