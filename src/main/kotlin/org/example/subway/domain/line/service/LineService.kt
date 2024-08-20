package org.example.subway.domain.line.service

import org.example.subway.domain.line.dto.CreateLineRequest
import org.example.subway.domain.line.dto.CreateLineResponse
import org.example.subway.domain.line.entity.Line
import org.example.subway.domain.line.repository.LineRepository
import org.example.subway.domain.station.repository.StationRepository
import org.example.subway.domain.station.repository.findByIdOrThrow
import org.example.subway.global.exception.SubwayCustomException
import org.example.subway.global.exception.SubwayErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LineService(
    private val lineRepository: LineRepository,
    private val stationRepository: StationRepository
) {
    @Transactional
    fun createLine(request: CreateLineRequest): CreateLineResponse {
        val upStation = stationRepository.findByIdOrThrow(request.upStationId)
        val downStation = stationRepository.findByIdOrThrow(request.downStationId)

        if (lineRepository.existsByName(request.name)) {
            throw SubwayCustomException(SubwayErrorCode.LINE_NAME_IS_DUPLICATED)
        }

        val line = Line.of(
            color = request.color,
            distance = request.distance,
            name = request.name,
            downStation = downStation,
            upStation = upStation
        ).apply { lineRepository.save(this) }

        return CreateLineResponse.from(line)
    }
}