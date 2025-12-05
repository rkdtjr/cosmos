package kr.ac.kumoh.s20220744.cosmos.controller

import kr.ac.kumoh.s20220744.cosmos.model.APOD
import kr.ac.kumoh.s20220744.cosmos.service.APODService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:5173", "https://rkdtjr.github.io"])
@RestController
@RequestMapping("/api/apod")
class APODController(
    private val apodService: APODService
) {
    @GetMapping
    fun getAPOD(@RequestParam(required = false) date: String?): ResponseEntity<APOD> =
        ResponseEntity.ok(apodService.getAPOD(date))
}