package kr.ac.kumoh.s20220744.cosmos.sevice

import kr.ac.kumoh.s20220744.cosmos.dto.APODApiResponse
import kr.ac.kumoh.s20220744.cosmos.model.APOD
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

class NasaApiService(
    @Value("\${nasa.apiKey}")
    private var apiKey: String
) {
    val rest = RestTemplate()

    private fun fetchAndSaveAPOD(date: String): APOD {
        fun fetchAPOD(date: String): APODApiResponse {
            val url = "https://api.nasa.gov/planetary/apod?api_key=$apiKey&date=$date"
            return rest.getForObject(url, APODApiResponse::class.java)
                ?: throw IllegalStateException("NASA APOD API returned null")
        }
    }
}