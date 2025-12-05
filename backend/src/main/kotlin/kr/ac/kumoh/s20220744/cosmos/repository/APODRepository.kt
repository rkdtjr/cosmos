package kr.ac.kumoh.s20220744.cosmos.repository

import kr.ac.kumoh.s20220744.cosmos.model.APOD
import org.springframework.data.mongodb.repository.MongoRepository

interface APODRepository : MongoRepository<APOD,String> {
}