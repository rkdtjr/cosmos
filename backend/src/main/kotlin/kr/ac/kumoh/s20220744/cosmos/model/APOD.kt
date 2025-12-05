package kr.ac.kumoh.s20220744.cosmos.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "apod")
data class APOD(
    @Id val date: String,
    val title: String,
    val explanation: String,
    val url: String?,
    val hdurl: String?,
    val copyright: String?,
    val cachedAt: LocalDateTime = LocalDateTime.now()
)
