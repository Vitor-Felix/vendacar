package com.xotril.vendacar.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "sales")
data class Sale(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val buyerCpf: String,
    val saleDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,

    @Column(unique = true)
    val paymentCode: String = UUID.randomUUID().toString(),

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    val vehicle: Vehicle
)

enum class PaymentStatus {
    PENDING,
    APPROVED,
    CANCELLED
}
