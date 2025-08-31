package com.xotril.vendacar.infra.persistence

import com.xotril.vendacar.domain.model.Sale
import com.xotril.vendacar.domain.repository.SaleRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringDataSaleRepository : JpaRepository<Sale, Long>

@Repository
class JpaSaleRepository(
    private val springRepo: SpringDataSaleRepository
) : SaleRepository {
    override fun save(sale: Sale): Sale = springRepo.save(sale)
}
