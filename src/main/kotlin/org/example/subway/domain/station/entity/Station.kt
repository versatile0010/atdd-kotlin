package org.example.subway.domain.station.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.example.subway.config.entity.BaseEntity

@Entity
class Station(
    @Column
    val name: String
) : BaseEntity() {
    companion object {
        fun from(name: String): Station {
            return Station(name = name)
        }
    }
}