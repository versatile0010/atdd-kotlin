package org.example.subway.global.exception

data class CustomErrorResponse(
    val errorCode: String,
    val message: String
) {
    companion object {
        fun of(errorCode: String, message: String): CustomErrorResponse {
            return CustomErrorResponse(
                errorCode = errorCode,
                message = message
            )
        }

        fun from(subwayErrorCode: SubwayErrorCode): CustomErrorResponse {
            return CustomErrorResponse(
                errorCode = subwayErrorCode.code,
                message = subwayErrorCode.message
            )
        }
    }
}