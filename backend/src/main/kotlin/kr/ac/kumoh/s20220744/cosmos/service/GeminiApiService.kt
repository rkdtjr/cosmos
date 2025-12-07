package kr.ac.kumoh.s20220744.cosmos.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.genai.Client
import com.google.genai.types.GenerateContentResponse
import kr.ac.kumoh.s20220744.cosmos.repository.SpaceImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class GeminiApiService(
    @Value("\${gemini.apiKey}")
    private val apiKey: String,
    private val restClient: RestClient,
    private val spaceImageRepository: SpaceImageRepository
) {
    @Scheduled(fixedDelay = 20000)
    fun processQueue() {
        val pendingList = spaceImageRepository.findTop5ByStatusOrderByCreatedAtAsc("PENDING")

        pendingList.forEach { img ->
            val (isSpace, tags) = analyzeSpaceImage(
                img.originalUrl ?: img.previewUrl,
                img.description
            )

            val updated = img.copy(
                tags = tags,
                status = if (isSpace) "DONE" else "FAILED"
            )

            spaceImageRepository.save(updated)
        }
    }
    fun analyzeSpaceImage(imageUrl: String?, description: String?): Pair<Boolean, List<String>> {
        if (imageUrl.isNullOrBlank()) return false to emptyList()

        val client = Client.builder().apiKey(apiKey).build()

        val prompt = """
            You are an astronomy domain expert with deep knowledge of space imagery.
            Strictly determine whether the image described below is clearly an astronomy or space-related image.
            
            Classify an image as space-related if it contains any of the following:
            - Stars, galaxies, nebulae, star clusters, wide star fields, deep field exposures
            - Planets, moons, comets, asteroids
            - Space telescopes (Hubble, JWST, ISS), spacecraft, rocket launches, observatories
            - Outer space background (dark sky with dense stars)
            
            Even if the image is faint or low-resolution, consider deep field star images as space images.
            
            NASA Metadata:
            $description
            
            Image URL:
            $imageUrl
            
            Output format:
            1) "yes" or "no" only in the first line
            2) If yes, output 3–5 short descriptive tags in JSON array format on the second line
            """.trimIndent()

        return try {
            val response: GenerateContentResponse = client.models.generateContent(
                "gemini-2.5-flash-lite",
                prompt,
                null
            )

            val raw = response.text()?.trim().orEmpty()
            val lines = raw.split("\n")
            val isSpace = lines.firstOrNull()?.lowercase() == "yes"

            val tags = if (isSpace && lines.size > 1) {
                try {
                    jacksonObjectMapper().readValue(lines[1], List::class.java)
                        .map { it.toString() }
                } catch (_: Exception) {
                    emptyList()
                }
            } else emptyList()
            println("Tagging Success")
            isSpace to tags

        } catch (e: Exception) {
            if (e.message?.contains("429") == true) {
                println("Rate limited — skipping.")
            } else {
                println("Error: ${e.message}")
            }
            false to emptyList()
        }
    }


}