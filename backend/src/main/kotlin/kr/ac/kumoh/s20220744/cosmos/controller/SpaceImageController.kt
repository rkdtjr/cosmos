package kr.ac.kumoh.s20220744.cosmos.controller

import kr.ac.kumoh.s20220744.cosmos.model.SpaceImage
import kr.ac.kumoh.s20220744.cosmos.service.SpaceImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/gallery")
class SpaceImageController(
    private val spaceImageService: SpaceImageService
) {
    @GetMapping("/random")
    fun getRandomImage(): ResponseEntity<List<SpaceImage>> = ResponseEntity.ok(spaceImageService.getRandomImage())

    @GetMapping("/search")
    fun searchImage(@RequestParam keyword: String): ResponseEntity<List<SpaceImage>> = ResponseEntity.ok(spaceImageService.searchImage(keyword))
}