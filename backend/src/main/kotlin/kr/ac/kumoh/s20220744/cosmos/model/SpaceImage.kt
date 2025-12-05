package kr.ac.kumoh.s20220744.cosmos.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "space_image")
data class SpaceImage(
    @Id val nasaId: String,
    val title: String?,
    val description: String?,
    val previewUrl: String?,
    val originalUrl: String?,
    val tags: List<String> = emptyList(),
    val createdAt: String? = null,
    val cachedAt: String = LocalDateTime.now().toString(),
    val status: String = "PENDING"
)