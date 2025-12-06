package kr.ac.kumoh.s20220744.cosmos.controller

import kr.ac.kumoh.s20220744.cosmos.dto.DictionaryListDto
import kr.ac.kumoh.s20220744.cosmos.model.Dictionary
import kr.ac.kumoh.s20220744.cosmos.service.DictionaryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:5173", "https://rkdtjr.github.io"])
@RestController
@RequestMapping("api/dictionary")
class DictionaryController(
    private val dictionaryService: DictionaryService
) {
    @GetMapping
    @GetMapping("/dictionary")
    fun list(): List<DictionaryListDto> =
        dictionaryService.getDictionaryList()

    @GetMapping("/dictionary/{id}")
    fun detail(@PathVariable id: String) =
        dictionaryService.getDictionaryDetail(id)
}