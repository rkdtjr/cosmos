package kr.ac.kumoh.s20220744.cosmos.service

import kr.ac.kumoh.s20220744.cosmos.model.SpaceImage
import kr.ac.kumoh.s20220744.cosmos.repository.SpaceImageRepository
import org.springframework.stereotype.Service

@Service
class SpaceImageService(
    private val spaceImageRepository: SpaceImageRepository,
    private val nasaApiService: NasaApiService,
    private val geminiApiService: GeminiApiService
) {
    fun getRandomImage(): List<SpaceImage> = spaceImageRepository.findRandomImage()

    fun searchImage(keyword: String, page: Int ): List<SpaceImage> {
        val nasaList = nasaApiService.searchNasaImage(keyword,page)
        val result = mutableListOf<SpaceImage>()

        nasaList.forEach { nasa ->
            val imageUrl = nasa.originalUrl ?: nasa.previewUrl ?: return@forEach

            val exist = spaceImageRepository.findById(nasa.nasaId).orElse(null)
            if (exist != null ) {
                if (exist.status != "FAILED") {
                    result.add(exist)
                }
                return@forEach
            }
            // DB에 기본 데이터 먼저 저장 (tags는 비어있음)
            val saved = spaceImageRepository.save(
                SpaceImage(
                    nasaId = nasa.nasaId,
                    title = nasa.title ?: "",
                    description = nasa.description ?: "",
                    tags = emptyList(),
                    previewUrl = nasa.previewUrl,
                    originalUrl = nasa.originalUrl,
                    createdAt = nasa.createdAt,
                    status = "PENDING"
                )
            )
            result.add(saved)

        }

        return result
    }
}