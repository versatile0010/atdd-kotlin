package org.example.subway.domain.station.repository

import org.example.subway.domain.station.entity.Station
import org.example.subway.global.exception.SubwayCustomException
import org.example.subway.global.exception.SubwayErrorCode
import org.springframework.data.jpa.repository.JpaRepository

fun StationRepository.findByIdOrThrow(stationId: Long): Station = findById(stationId)
    .orElseThrow { SubwayCustomException(SubwayErrorCode.SUBWAY_IS_NOT_FOUND) }

interface StationRepository : JpaRepository<Station, Long> {
    fun existsByName(name: String): Boolean
}