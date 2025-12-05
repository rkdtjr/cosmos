package kr.ac.kumoh.s20220744.cosmos.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "dictionary")
data class Dictionary(
    @Id
    val id: String,
    val name: String,
    val type: String,

    val description: String,
    val relatedTags: List<String> = emptyList(),
    val imageRef: String?,
    val createdAt: LocalDateTime = LocalDateTime.now()
)