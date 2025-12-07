package kr.ac.kumoh.s20220744.cosmos.dto

data class APODApiResponse(
    val title: String,
    val explanation: String,
    val url: String?,
    val hdurl: String?,
    val copyright: String?
)