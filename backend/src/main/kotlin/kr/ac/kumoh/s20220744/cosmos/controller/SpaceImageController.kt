package kr.ac.kumoh.s20220744.cosmos.controller

import kr.ac.kumoh.s20220744.cosmos.model.SpaceImage
import kr.ac.kumoh.s20220744.cosmos.service.SpaceImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:5173", "https://rkdtjr.github.io"])
@RestController
@RequestMapping("/api/gallery")
class SpaceImageController(
    private val spaceImageService: SpaceImageService
) {
    @GetMapping("/search")
    fun searchImage(@RequestParam keyword: String, @RequestParam(defaultValue = "1") page: Int): ResponseEntity<List<SpaceImage>> {
        if(keyword.isBlank()) return ResponseEntity.ok(spaceImageService.getRandomImage())

        return ResponseEntity.ok(spaceImageService.searchImage(keyword,page))
    }
}