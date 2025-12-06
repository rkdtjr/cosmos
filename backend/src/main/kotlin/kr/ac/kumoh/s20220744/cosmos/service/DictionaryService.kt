package kr.ac.kumoh.s20220744.cosmos.service

import kr.ac.kumoh.s20220744.cosmos.dto.DictionaryListDto
import kr.ac.kumoh.s20220744.cosmos.repository.DictionaryRepository
import org.springframework.stereotype.Service

@Service
class DictionaryService(
    private val dictionaryRepository: DictionaryRepository
) {
    fun getDictionaryList(): List<DictionaryListDto> =
        dictionaryRepository.findAll().map {
            DictionaryListDto(
                id = it.id,
                name = it.name,
                type = it.type
            )
        }

    fun getDictionaryDetail(id: String) =
        dictionaryRepository.findById(id).orElse(null) // 그대로 반환
}