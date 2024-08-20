package org.example.subway.acceptance.domain.station

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.example.subway.AcceptanceTest
import org.example.subway.domain.line.dto.CreateLineRequest
import org.example.subway.domain.line.repository.LineRepository
import org.example.subway.domain.line.service.LineService
import org.example.subway.domain.station.entity.Station
import org.example.subway.domain.station.repository.StationRepository
import org.example.subway.sendPost
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class LineAcceptanceTest : AcceptanceTest() {

    @Autowired
    lateinit var lineService: LineService

    @Autowired
    lateinit var stationRepository: StationRepository

    @Autowired
    lateinit var lineRepository: LineRepository

    @Test
    @DisplayName("새로운 노선을 생성할 수 있다.")
    fun 노선_생성_테스트() {
        // given 2 개의 지하철 역을 생성하고
        val 강동역 = Station.from("강동역")
        val 천호역 = Station.from("천호역")

        stationRepository.saveAll(mutableListOf(강동역, 천호역))
        val request = CreateLineRequest(
            upStationId = 강동역.id!!,
            downStationId = 천호역.id!!,
            name = "5호선",
            distance = 10,
            color = "보라색"
        )
        // when 새로운 노선에 상행 종점역과 하행 종점역으로 요청을 보내면
        val extract = RestAssured.given().sendPost(LINE_PATH, request)

        // then 노선이 생성되어야 한다.
        Assertions.assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    companion object {
        val LINE_PATH: String = "/lines"
    }
}