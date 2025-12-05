package kr.ac.kumoh.s20220744.cosmos.repository

import kr.ac.kumoh.s20220744.cosmos.model.Dictionary
import org.springframework.data.mongodb.repository.MongoRepository

interface DictionaryRepository: MongoRepository<Dictionary, String> {
}