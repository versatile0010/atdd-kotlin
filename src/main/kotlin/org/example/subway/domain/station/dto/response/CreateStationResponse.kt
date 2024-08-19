package org.example.subway.domain.station.dto.response

import org.example.subway.domain.station.entity.Station

data class CreateStationResponse(
    val id: Long?
) {
    companion object {
        fun from(station: Station): CreateStationResponse {
            return CreateStationResponse(
                id = station.id
            )
        }
    }
}
