package com.xotril.vendacar.app

import com.xotril.vendacar.app.usecase.*
import com.xotril.vendacar.domain.repository.SaleRepositoryPort
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig(
    private val vehicleRepositoryPort: VehicleRepositoryPort,
    private val saleRepositoryPort: SaleRepositoryPort
) {

    @Bean
    fun createVehicleUseCase() = CreateVehicleUseCase(vehicleRepositoryPort)

    @Bean
    fun updateVehicleUseCase() = UpdateVehicleUseCase(vehicleRepositoryPort)

    @Bean
    fun sellVehicleUseCase() = BuyVehicleUseCase(vehicleRepositoryPort, saleRepositoryPort)

    @Bean
    fun listVehiclesUseCase() = ListVehiclesUseCase(vehicleRepositoryPort)

    @Bean
    fun updatePaymentStatusUseCase() =
        UpdatePaymentStatusUseCase(saleRepositoryPort, vehicleRepositoryPort)
}
