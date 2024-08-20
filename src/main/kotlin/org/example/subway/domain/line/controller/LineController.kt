package org.example.subway.domain.line.controller

import org.example.subway.domain.line.dto.CreateLineRequest
import org.example.subway.domain.line.dto.CreateLineResponse
import org.example.subway.domain.line.service.LineService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RequestMapping("/lines")
@RestController
class LineController(
    private val lineService: LineService
) {
    @PostMapping
    fun createLine(@RequestBody request: CreateLineRequest): ResponseEntity<CreateLineResponse> {
        return ResponseEntity.created(URI.create("/lines")).body(lineService.createLine(request))
    }
}
