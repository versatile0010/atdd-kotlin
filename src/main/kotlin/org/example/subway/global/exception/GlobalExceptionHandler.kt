package org.example.subway.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [SubwayCustomException::class])
    fun handleCustomException(exception: SubwayCustomException): ResponseEntity<CustomErrorResponse> {
        val errorCode = exception.subwayErrorCode
        val errorResponse = CustomErrorResponse.of(errorCode = errorCode.code, message = errorCode.message);
        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(value = [Exception::class])
    fun unhandledException(exception: Exception): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse.from(SubwayErrorCode.UNHANDLED_EXCEPTION)
        return ResponseEntity.internalServerError().body(errorResponse)
    }

}