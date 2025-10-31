package com.xotril.vendacar.infra.persistence

import com.xotril.vendacar.domain.model.Sale
import com.xotril.vendacar.domain.repository.SaleRepositoryPort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataSaleRepository : JpaRepository<Sale, Long>{
    fun findByPaymentCode(paymentCode: String): Sale?
}

@Repository
class JpaSaleRepositoryAdapter(
    private val springRepo: SpringDataSaleRepository
) : SaleRepositoryPort {
    override fun save(sale: Sale): Sale = springRepo.save(sale)

    override fun findByPaymentCode(paymentCode: String): Sale? =
        springRepo.findByPaymentCode(paymentCode)
}
