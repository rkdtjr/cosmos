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
    private val apodRepository: APODReposiotry,
    private val nasa: NasaApiService
) {
    fun getAPOD(date: String?): APOD {
        val targetDate = date ?: LocalDate.now().minusDays(1).toString()

        return apodRepository.findById(targetDate).orElse(null) ?: fetchAndSave(targetDate)
    }

    private fun fetchAndSave(date: String): APOD {
        val nasaRes = nasa.fetchAPOD(date)

        val apod = APOD(
            date = date,
            title = nasaRes.title,
            explanation = nasaRes.explanation,
            url = nasaRes.url,
            hdurl = nasaRes.hdurl,
            copyright = nasaRes.copyright
        )

        return apodRepository.save(apod)
    }
}