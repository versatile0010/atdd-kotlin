package org.example.subway.domain.station.repository

import org.example.subway.domain.station.entity.Station
import org.springframework.data.jpa.repository.JpaRepository

interface StationRepository : JpaRepository<Station, Long> {
    fun existsByName(name: String): Boolean
}