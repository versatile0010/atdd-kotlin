package org.example.subway.acceptance

import io.restassured.RestAssured
import org.example.subway.AcceptanceTest
import org.junit.jupiter.api.Test

class RestTest : AcceptanceTest() {
    @Test
    fun restTest() {
        RestAssured.given().log().all()
            .`when`().get("https://google.com")
            .then().log().all()
            .extract()
    }
}