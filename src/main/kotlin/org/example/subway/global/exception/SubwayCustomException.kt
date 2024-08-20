package org.example.subway.global.exception

class SubwayCustomException(
    val subwayErrorCode: SubwayErrorCode
) : RuntimeException()