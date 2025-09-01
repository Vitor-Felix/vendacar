package com.xotril.vendacar.domain.repository

import com.xotril.vendacar.domain.model.Sale

interface SaleRepositoryPort {
    fun save(sale: Sale): Sale
}
