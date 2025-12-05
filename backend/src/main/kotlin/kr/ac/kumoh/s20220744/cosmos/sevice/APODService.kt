package kr.ac.kumoh.s20220744.cosmos.sevice

import kr.ac.kumoh.s20220744.cosmos.dto.APODApiResponse
import kr.ac.kumoh.s20220744.cosmos.model.APOD
import kr.ac.kumoh.s20220744.cosmos.repository.APODReposiotry
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDate

@Service
class APODService(
    private val apodRepository: APODReposiotry
) {
    @Value("\${nasa.apiKey}")
    lateinit var apiKey: String
    val rest = RestTemplate()

    fun getAPOD(date: String?): APOD {
        val targetDate = date ?: LocalDate.now().minusDays(1).toString()

        return apodRepository.findById(targetDate).orElse(null) ?: fetchAndSaveAPOD(targetDate)
    }

    private fun fetchAndSaveAPOD(date: String): APOD {
        val url = "https://api.nasa.gov/planetary/apod?api_key=$apiKey&date=$date"
        val nasa = rest.getForObject(url, APODApiResponse::class.java)
            ?: throw IllegalStateException("NASA APOD API returned null")

        val apod = APOD(
            date = date,
            title = nasa.title,
            explanation = nasa.explanation,
            url = nasa.url,
            hdurl = nasa.hdurl,
            copyright = nasa.copyright
        )

        return apodRepository.save(apod)
    }
}