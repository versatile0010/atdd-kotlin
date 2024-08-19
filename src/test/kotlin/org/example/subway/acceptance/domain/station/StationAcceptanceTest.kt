package org.example.subway.acceptance.domain.station

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.example.subway.AcceptanceTest
import org.example.subway.domain.station.dto.request.CreateStationRequest
import org.example.subway.domain.station.dto.response.GetStationResponse
import org.example.subway.domain.station.entity.Station
import org.example.subway.domain.station.repository.StationRepository
import org.example.subway.domain.station.service.StationService
import org.example.subway.getList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StationAcceptanceTest : AcceptanceTest() {

    @Autowired
    lateinit var stationService: StationService

    @Autowired
    lateinit var stationRepository: StationRepository

    @Test
    @DisplayName("지하철 역을 생성할 수 있다.")
    fun 역_생성_테스트() {
        // when 지하철 역 생성 API 을 호출하면
        val extract = RestAssured.given().log().all()
            .body(CreateStationRequest("강동역")).contentType(ContentType.JSON)
            .`when`().post(STATION_PATH)
            .then().log().all()
            .extract();

        // then 지하철 역이 생성된다. 그리고 지하철역 목록 조회 시 생성한 역을 찾을 수 있다.
        assertEquals(HttpStatus.CREATED.value(), extract.statusCode())

        val stations = stationService.findAllStations()
        val stationNames = stations.map { it.name }
        assertTrue(stationNames.contains("강동역"), "생성된 지하철 역이 노선에 존재해야 합니다.")
    }

    @Test
    @DisplayName("지하철 목록을 조회하면, 생성된 역을 모두 확인할 수 있다.")
    fun 역_목록_조회_테스트() {
        // given 2 개의 지하철 역을 생성하고
        stationRepository.saveAll(mutableListOf(Station.from("강동역"), Station.from("천호역")))

        // when 지하철역 목록을 조회하면
        val extract = RestAssured.given().log().all()
            .`when`().get(STATION_PATH)
            .then().log().all()
            .extract()

        // then 2 개의 지하철역을 응답 받는다. 생성한 지하철 역으로만 이루어진 노선이어야 한다.
        assertEquals(HttpStatus.OK.value(), extract.statusCode())

        val stations: List<GetStationResponse> = extract.getList<GetStationResponse>()
        assertEquals(2, stations.size)
        assertTrue(stations.any { it.name == "강동역" }, "강동역이 목록에 포함되어 있어야 합니다.")
        assertTrue(stations.any { it.name == "천호역" }, "천호역이 목록에 포함되어 있어야 합니다.")

    }

    /**
     * *     * Given 지하철역을 생성하고
     *     * When 그 지하철역을 삭제하면
     *     * Then 그 지하철역 목록 조회 시 생성한 역을 찾을 수 없다
     */
    @Test
    @DisplayName("생성된 지하철 역을 제거할 수 있다.")
    fun 역_삭제_테스트() {
        // given 지하철 역을 생성하고
        val 강동역 = stationRepository.save(Station.from("강동역"))

        // when 해당 지하철 역을 삭제하면
        val extract = RestAssured.given().log().all()
            .`when`().delete("$STATION_PATH/${강동역.id}")
            .then().log().all()
            .extract()

        // then 목록 조회 시 생성한 역을 찾을 수 없다.
        assertEquals(HttpStatus.NO_CONTENT.value(), extract.statusCode())
        val stations = stationService.findAllStations()
        assertFalse(stations.any { it.name == "강동역" }, "강동역을 삭제한 뒤에는 목록에 포함되어 있으면 안됩니다.")
    }

    companion object {
        private const val STATION_PATH = "/stations"
    }
}