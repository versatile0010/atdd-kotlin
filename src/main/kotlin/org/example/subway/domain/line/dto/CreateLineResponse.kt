package org.example.subway.domain.line.dto

import org.example.subway.domain.line.entity.Line

data class CreateLineResponse(val id: Long?) {
    companion object {
        fun from(line: Line): CreateLineResponse {
            return CreateLineResponse(id = line.id)
        }
    }
}