package com.xotril.vendacar.web.controller

import com.xotril.vendacar.app.VehicleFacade
import com.xotril.vendacar.web.mapper.toDomain
import com.xotril.vendacar.web.mapper.toResponse
import com.xotril.vendacar.web.request.VehicleRequest
import com.xotril.vendacar.web.response.VehicleResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleController(private val vehicleFacade: VehicleFacade) {

    @Operation(
        summary = "Register a vehicle for sale",
        description = "Creates a new vehicle listing with brand, model, year, color, and price",
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
        summary = "Buy a vehicle",
        description = "Buyer must be logged in",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")]
    )
    @PostMapping("/{id}/buy")
    fun buyVehicle(
        @PathVariable id: Long,
        authentication: org.springframework.security.core.Authentication
    ): VehicleResponse {

        val token = authentication.principal as org.springframework.security.oauth2.jwt.Jwt
        val cpf = token.getClaimAsString("cpf")

        val vehicle = vehicleFacade.buyVehicle(id, cpf)
        return vehicle.toResponse()
    }
}
