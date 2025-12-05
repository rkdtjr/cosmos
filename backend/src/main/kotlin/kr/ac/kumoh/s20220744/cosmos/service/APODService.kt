package kr.ac.kumoh.s20220744.cosmos.service

import kr.ac.kumoh.s20220744.cosmos.model.APOD
import kr.ac.kumoh.s20220744.cosmos.repository.APODRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class APODService(
    private val apodRepository: APODRepository,
    private val nasa: NasaApiService
) {
    fun getAPOD(date: String?): APOD {
        val targetDate = date ?: LocalDate.now().minusDays(1).toString()

        return apodRepository.findById(targetDate).orElse(null) ?: fetchAndSave(targetDate)
    }

    private fun fetchAndSave(date: String): APOD {
        val nasaRes = nasa.fetchAPOD(date)

        return apodRepository.save(nasaRes)
    }
}