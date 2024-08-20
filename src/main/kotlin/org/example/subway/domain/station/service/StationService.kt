package org.example.subway.domain.station.service

import org.example.subway.domain.station.dto.request.CreateStationRequest
import org.example.subway.domain.station.dto.response.CreateStationResponse
import org.example.subway.domain.station.dto.response.GetStationResponse
import org.example.subway.domain.station.entity.Station
import org.example.subway.domain.station.repository.StationRepository
import org.example.subway.global.exception.SubwayCustomException
import org.example.subway.global.exception.SubwayErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StationService(
    private val stationRepository: StationRepository
) {
    @Transactional
    fun createStation(request: CreateStationRequest): CreateStationResponse {
        if (stationRepository.existsByName(request.name)) {
            throw SubwayCustomException(SubwayErrorCode.SUBWAY_NAME_IS_DUPLICATED)
        }
        val station = stationRepository.save(Station.from(request.name))
        return CreateStationResponse.from(station)
    }

    fun findStationById(stationId: Long): GetStationResponse {
        val station = stationRepository.findById(stationId)
            .orElseThrow { SubwayCustomException(SubwayErrorCode.SUBWAY_IS_NOT_FOUND) }

        return GetStationResponse.from(station)
    }

    fun findAllStations(): List<GetStationResponse> {
        return stationRepository.findAll()
            .map(GetStationResponse::from)
            .toList()
    }

    @Transactional
    fun deleteStation(stationId: Long) {
        stationRepository.deleteById(stationId)
    }
}
