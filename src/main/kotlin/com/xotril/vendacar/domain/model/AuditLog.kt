package com.xotril.vendacar.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "audit_logs")
data class AuditLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val action: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    val vehicle: Vehicle
)
