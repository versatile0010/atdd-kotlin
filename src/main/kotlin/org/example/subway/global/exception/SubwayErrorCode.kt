package org.example.subway.global.exception

enum class SubwayErrorCode(
    val code: String,
    val message: String
) {
    SUBWAY_NAME_IS_DUPLICATED("SUBWAY-001", "이미 중복된 이름의 지하철 역이 존재합니다."),
    SUBWAY_IS_NOT_FOUND("SUBWAY-002", "해당 지하철 역을 찾을 수 없습니다."),

    LINE_NAME_IS_DUPLICATED("LINE-001", "이미 중복된 이름의 지하철 노선이 존재합니다"),

    UNHANDLED_EXCEPTION("CRITICAL-ERROR-001", "일시적으로 서버 상태가 온전하지 않습니다.")
}