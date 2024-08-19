package org.example.subway.domain.station.controller

import org.example.subway.domain.station.dto.request.CreateStationRequest
import org.example.subway.domain.station.dto.response.CreateStationResponse
import org.example.subway.domain.station.dto.response.GetStationResponse
import org.example.subway.domain.station.service.StationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RequestMapping("/stations")
@RestController
class StationController(
    private val stationService: StationService
) {
    @PostMapping
    fun createStation(@RequestBody request: CreateStationRequest): ResponseEntity<CreateStationResponse> {
        return ResponseEntity.created(URI.create("/stations")).body(stationService.createStation(request))
    }

    @GetMapping("/{station-id}")
    fun getStation(@PathVariable(name = "station-id") stationId: Long): ResponseEntity<GetStationResponse> {
        return ResponseEntity.ok(stationService.findStationById(stationId))
    }

    @GetMapping
    fun getStations(): ResponseEntity<List<GetStationResponse>> {
        return ResponseEntity.ok(stationService.findAllStations())
    }

    @DeleteMapping("/{station-id}")
    fun deleteStation(@PathVariable(name = "station-id") stationId: Long): ResponseEntity<Void> {
        stationService.deleteStation(stationId)
        return ResponseEntity.noContent().build()
    }
}
