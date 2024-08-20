package org.example.subway.global.exception

class CustomException(
    val subwayErrorCode: SubwayErrorCode
) : RuntimeException()