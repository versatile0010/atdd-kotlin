package org.example.subway.domain.station.dto.response

import org.example.subway.domain.station.entity.Station

data class GetStationResponse(
    val id: Long?,
    val name: String
) {
    companion object {
        fun from(station: Station): GetStationResponse {
            return GetStationResponse(
                id = station.id,
                name = station.name
            )
        }
    }
}
