package org.example.subway.domain.station.service

import org.example.subway.domain.station.dto.request.CreateStationRequest
import org.example.subway.domain.station.dto.response.CreateStationResponse
import org.example.subway.domain.station.dto.response.GetStationResponse
import org.example.subway.domain.station.entity.Station
import org.example.subway.domain.station.repository.StationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StationService(
    private val stationRepository: StationRepository
) {
    @Transactional
    fun createStation(request: CreateStationRequest): CreateStationResponse {
        require(!stationRepository.existsByName(request.name)) {
            "해당 이름의 지하철 역은 이미 존재합니다."
        }
        val station = stationRepository.save(Station.from(request.name))
        return CreateStationResponse.from(station)
    }

    fun findStationById(stationId: Long): GetStationResponse {
        val station = stationRepository.findById(stationId)
            .orElseThrow { IllegalArgumentException("해당 지하철 역을 찾을 수 없습니다.") }

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
