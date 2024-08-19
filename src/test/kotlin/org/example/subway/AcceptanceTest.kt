package org.example.subway

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {
    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @LocalServerPort
    var port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        databaseCleanUp.execute()
    }

}


fun RequestSpecification.sendGet(path: String): ExtractableResponse<Response> {
    return this.log().all()
        .`when`().get(path)
        .then().log().all()
        .extract()
}

fun RequestSpecification.sendPost(
    path: String,
    body: Any,
    contentType: ContentType = ContentType.JSON
): ExtractableResponse<Response> {
    return this.log().all()
        .body(body).contentType(contentType)
        .`when`().post(path)
        .then().log().all()
        .extract()
}

fun RequestSpecification.sendDelete(path: String): ExtractableResponse<Response> {
    return this.log().all()
        .`when`().delete(path)
        .then().log().all()
        .extract()
}

inline fun <reified T> ExtractableResponse<Response>.getList(): List<T> {
    return this.jsonPath().getList(".", T::class.java)
}