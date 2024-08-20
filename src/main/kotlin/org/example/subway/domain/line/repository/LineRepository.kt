package org.example.subway.domain.line.repository

import org.example.subway.domain.line.entity.Line
import org.springframework.data.jpa.repository.JpaRepository

interface LineRepository : JpaRepository<Line, Long> {
    fun existsByName(name: String) : Boolean
}