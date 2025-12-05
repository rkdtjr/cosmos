package kr.ac.kumoh.s20220744.cosmos.dto

data class NasaImage(
    val nasaId: String,
    val title: String?,
    val description: String?,
    val previewUrl: String?,
    val originalUrl: String?,
    val createdAt: String?
)