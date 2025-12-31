package com.xotril.vendacar.app

import com.xotril.vendacar.app.usecase.CreateVehicleUseCase
import com.xotril.vendacar.app.usecase.ListVehiclesUseCase
import com.xotril.vendacar.app.usecase.BuyVehicleUseCase
import com.xotril.vendacar.app.usecase.UpdatePaymentStatusUseCase
import com.xotril.vendacar.app.usecase.UpdateVehicleUseCase
import com.xotril.vendacar.domain.model.Vehicle
import com.xotril.vendacar.web.request.VehicleRequest
import org.springframework.stereotype.Service

@Service
class VehicleFacade(
    private val createVehicleUseCase: CreateVehicleUseCase,
    private val updateVehicleUseCase: UpdateVehicleUseCase,
    private val buyVehicleUseCase: BuyVehicleUseCase,
    private val listVehiclesUseCase: ListVehiclesUseCase,
    private val updatePaymentStatusUseCase: UpdatePaymentStatusUseCase
) {

    fun createVehicle(vehicle: Vehicle): Vehicle =
        createVehicleUseCase.execute(vehicle)

    fun updateVehicle(vehicleId: Long, request: VehicleRequest): Vehicle =
        updateVehicleUseCase.execute(vehicleId, request)

    fun buyVehicle(vehicleId: Long, buyerCpf: String): Vehicle =
        buyVehicleUseCase.execute(vehicleId, buyerCpf)

    fun findVehicleById(id: Long): Vehicle? =
        listVehiclesUseCase.execute(null, false).find { it.id == id }

    fun listVehicles(sold: Boolean?, orderByPrice: Boolean): List<Vehicle> =
        listVehiclesUseCase.execute(sold, orderByPrice)

    fun updatePaymentStatus(paymentCode: String, status: String) =
        updatePaymentStatusUseCase.execute(paymentCode, status)
}
