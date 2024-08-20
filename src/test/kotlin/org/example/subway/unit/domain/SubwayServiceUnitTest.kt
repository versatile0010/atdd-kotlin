package org.example.subway.unit.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.subway.domain.station.dto.request.CreateStationRequest
import org.example.subway.domain.station.dto.response.CreateStationResponse
import org.example.subway.domain.station.dto.response.GetStationResponse
import org.example.subway.domain.station.entity.Station
import org.example.subway.domain.station.repository.StationRepository
import org.example.subway.domain.station.service.StationService
import org.example.subway.global.exception.SubwayCustomException
import java.util.*

class SubwayServiceUnitTest : BehaviorSpec({
    val stationRepository = mockk<StationRepository>()
    val stationService = StationService(stationRepository)

    given("지하철 역 생성 서비스 로직을 호출할 때") {
        `when`("새로운 역 이름이 중복되지 않은 경우") {
            val request = CreateStationRequest("강동역")
            val station = Station.from(request.name)
            val response = CreateStationResponse.from(station)

            every { stationRepository.existsByName(request.name) } returns false
            every { stationRepository.save(any()) } returns station

            then("새로운 역을 생성하고 정상 응답을 반환해야 한다.") {
                val result = stationService.createStation(request)
                result shouldBe response
            }
        }

        `when`("새로운 역 이름이 이미 존재하는 경우") {
            val request = CreateStationRequest("강동역")

            every { stationRepository.existsByName(request.name) } returns true

            then("예외가 발생해야 한다.") {
                shouldThrow<SubwayCustomException> {
                    stationService.createStation(request)
                }
            }
        }
    }

    given("지하철 역 단건 조회 서비스 로직을 호출하면") {
        `when`("찾으려는 역이 존재한다면") {
            val stationId = 1L
            val station = Station("강동역")
            val response = GetStationResponse.from(station)

            every { stationRepository.findById(stationId) } returns Optional.of(station)

            then("해당 역을 조회할 수 있어야 한다.") {
                val result = stationService.findStationById(stationId)
                result shouldBe response
            }
        }

        `when`("찾으려는 역이 존재하지 않는다면") {
            val stationId = 1L
            every { stationRepository.findById(stationId) } returns Optional.empty()
            then("예외가 발생해야 한다.") {
                shouldThrow<SubwayCustomException> {
                    stationService.findStationById(stationId)
                }
            }
        }
    }

    given("모든 지하철 역을 조회하는 서비스 로직을 호출하면") {
        `when`("여러 역이 존재한다면") {
            val stations = mutableListOf(Station("강동역"), Station("천호역"))
            val responses = stations.map { GetStationResponse.from(it) }

            every { stationRepository.findAll() } returns stations

            then("모든 역을 조회할 수 있어야 한다") {
                val result = stationService.findAllStations()

                result shouldBe responses
            }
        }
    }

    given("지하철 역을 삭제하는 서비스 로직을 호출하면") {
        val stationId = 1L
        every { stationRepository.deleteById(stationId) } returns Unit
        then("해당 역을 삭제해야 한다") {
            stationService.deleteStation(stationId)
            verify { stationRepository.deleteById(stationId) }
        }
    }
})
