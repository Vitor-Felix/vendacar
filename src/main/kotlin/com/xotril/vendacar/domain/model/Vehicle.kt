package com.xotril.vendacar.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "vehicles")
data class Vehicle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var brand: String,
    var model: String,
    var modelYear: Int,
    var color: String,
    var price: Double,

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
