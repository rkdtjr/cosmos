package kr.ac.kumoh.s20220744.cosmos.service

import kr.ac.kumoh.s20220744.cosmos.repository.DictionaryRepository
import org.springframework.stereotype.Service

@Service
class DictionaryService(
    private val dictionaryRepository: DictionaryRepository
) {
    fun getDictionaryList() = dictionaryRepository.findAll()

    fun getDictionaryDetial(id: String) = dictionaryRepository.findById(id).orElse(null)
}