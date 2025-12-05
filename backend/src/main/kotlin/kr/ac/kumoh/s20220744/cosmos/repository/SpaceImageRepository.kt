package kr.ac.kumoh.s20220744.cosmos.repository

import kr.ac.kumoh.s20220744.cosmos.model.SpaceImage
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface SpaceImageRepository: MongoRepository<SpaceImage, String> {
    @Aggregation(pipeline = [
        "{ \$sample: { size: 20 } }"
    ])
    fun findRandomImage(): List<SpaceImage>

    @Aggregation(pipeline = [
        "{ \$match: { 'description': { \$regex: ?0, \$options: 'i' } } }",
        "{ \$sample: { size: 20 } }"
    ])
    fun searchImage(keyword: String): List<SpaceImage>
}