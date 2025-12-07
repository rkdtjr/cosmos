package kr.ac.kumoh.s20220744.cosmos.repository

import kr.ac.kumoh.s20220744.cosmos.model.SpaceImage
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface SpaceImageRepository: MongoRepository<SpaceImage, String> {
    @Aggregation(pipeline = [
        "{ \$sample: { size: 20 } }"
    ])
    fun findRandomImage(): List<SpaceImage>

    fun findAllByStatusNot(status: String): List<SpaceImage>

    fun findTop5ByStatusOrderByCreatedAtAsc(status: String): List<SpaceImage>
}