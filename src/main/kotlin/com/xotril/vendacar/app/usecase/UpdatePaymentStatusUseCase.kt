package com.xotril.vendacar.app.usecase

import com.xotril.vendacar.domain.model.PaymentStatus
import com.xotril.vendacar.domain.repository.SaleRepositoryPort
import com.xotril.vendacar.domain.repository.VehicleRepositoryPort

class UpdatePaymentStatusUseCase(
    private val saleRepositoryPort: SaleRepositoryPort,
    private val vehicleRepositoryPort: VehicleRepositoryPort
) {

    fun execute(paymentCode: String, status: String) {
        val sale = saleRepositoryPort.findByPaymentCode(paymentCode)
            ?: throw IllegalArgumentException("Venda não encontrada para paymentCode $paymentCode")

        val newStatus = when (status.uppercase()) {
            "APPROVED" -> PaymentStatus.APPROVED
            "CANCELLED" -> PaymentStatus.CANCELLED
            else -> PaymentStatus.PENDING
        }

        sale.paymentStatus = newStatus

        if (newStatus == PaymentStatus.APPROVED) {
            sale.vehicle.sold = true
            vehicleRepositoryPort.save(sale.vehicle)
        }

        saleRepositoryPort.save(sale)
    }
}
