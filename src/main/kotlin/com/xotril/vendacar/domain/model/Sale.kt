package com.xotril.vendacar.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class Sale(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val buyerCpf: String,
    val saleDate: LocalDateTime = LocalDateTime.now(),

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    val vehicle: Vehicle
)
