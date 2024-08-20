package org.example.subway.domain.line.dto

data class CreateLineRequest(
    val upStationId: Long,
    val downStationId: Long,
    val name: String,
    val distance: Int,
    val color: String
)