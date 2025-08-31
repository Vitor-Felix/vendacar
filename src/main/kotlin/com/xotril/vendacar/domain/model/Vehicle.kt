package com.xotril.vendacar.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "vehicles")
data class Vehicle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val brand: String,
    val model: String,
    val modelYear: Int,
    val color: String,
    val price: Double,

    var sold: Boolean = false,

    @OneToOne(mappedBy = "vehicle", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sale: Sale? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null
) {
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
