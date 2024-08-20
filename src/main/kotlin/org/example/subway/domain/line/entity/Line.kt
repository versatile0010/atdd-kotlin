package org.example.subway.domain.line.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.example.subway.config.entity.BaseEntity
import org.example.subway.domain.station.entity.Station

@Entity
class Line(
    @Column
    val upStationId: Long?,
    @Column
    val downStationId: Long?,
    @Column
    val name: String,
    @Column
    val distance: Int,
    @Column
    val color: String
) : BaseEntity() {
    companion object {
        fun of(
            color: String,
            distance: Int,
            name: String,
            downStation: Station,
            upStation: Station
        ): Line {
            return Line(
                upStationId = upStation.id,
                downStationId = downStation.id,
                name = name,
                distance = distance,
                color = color
            )
        }
    }
}