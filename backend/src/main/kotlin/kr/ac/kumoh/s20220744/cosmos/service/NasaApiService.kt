package kr.ac.kumoh.s20220744.cosmos.service

import kr.ac.kumoh.s20220744.cosmos.dto.APODApiResponse
import kr.ac.kumoh.s20220744.cosmos.dto.NasaApiResponse
import kr.ac.kumoh.s20220744.cosmos.dto.NasaImage
import kr.ac.kumoh.s20220744.cosmos.model.APOD
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class NasaApiService(
    @Value("\${nasa.apiKey}")
    private var nasaKey: String,
    private val restClient: RestClient
) {
    fun fetchAPOD(date: String): APOD {
        val url = "https://api.nasa.gov/planetary/apod?api_key=$nasaKey&date=$date"

        val res = restClient.get().uri(url).retrieve().body(APODApiResponse::class.java) ?: throw IllegalStateException("Null Response")

        return APOD(
            date = date,
            title = res.title,
            explanation = res.explanation,
            url = res.url,
            hdurl = res.hdurl,
            copyright = res.copyright
        )
    }

    fun searchNasaImage(keyword: String, page: Int = 1): List<NasaImage> {
        val url = "https://images-api.nasa.gov/search?q=$keyword&media_type=image&page=$page"
        val res = restClient.get().uri(url).retrieve().body(NasaApiResponse::class.java)
            ?: throw IllegalStateException("Null Response")

        return res.collection.items.map { item ->
            val d = item.data.firstOrNull()
            NasaImage(
                nasaId = d?.nasa_id ?: "",
                title = d?.title,
                description = d?.description,
                previewUrl = item.links?.find { it.rel == "preview" }?.href,
                originalUrl = item.links?.find { it.rel == "preview" }?.href,
                createdAt = d?.date_created
            )
        }
    }
}