package kr.ac.kumoh.s20220744.cosmos.controller

import kr.ac.kumoh.s20220744.cosmos.model.APOD
import kr.ac.kumoh.s20220744.cosmos.service.APODService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/apod")
class APODController(
    private val apodService: APODService
) {
    @GetMapping
    fun getAPOD(@RequestParam(required = false) date: String?): ResponseEntity<APOD> =
        ResponseEntity.ok(apodService.getAPOD(date))
}