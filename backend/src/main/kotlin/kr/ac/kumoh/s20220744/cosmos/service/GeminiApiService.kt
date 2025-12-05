package kr.ac.kumoh.s20220744.cosmos.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kr.ac.kumoh.s20220744.cosmos.repository.SpaceImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class GeminiApiService(
    @Value("\${gemini.apiKey}")
    private val apiKey: String,
    private val restClient: RestClient,
    private val spaceImageRepository: SpaceImageRepository
) {
    @Async
    fun analyzeAsync(nasaId: String, imageUrl: String, description: String?) {
        try {
            val (isSpace, tags) = analyzeSpaceImage(imageUrl, description)
            val image = spaceImageRepository.findById(nasaId).orElse(null) ?: return

            val updated = image.copy(
                tags = tags,
                status = if (isSpace) "DONE" else "FAILED"
            )
            spaceImageRepository.save(updated)

        } catch (e: Exception) {
            val image = spaceImageRepository.findById(nasaId).orElse(null) ?: return
            val updated = image.copy(status = "RETRY")
            spaceImageRepository.save(updated)
        }
    }
    fun analyzeSpaceImage(imageUrl: String?, description: String?): Pair<Boolean, List<String>> {
        if (imageUrl.isNullOrBlank()) return false to emptyList()

        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=$apiKey"

        val prompt = """
            You are an astronomy expert.
            Determine whether the uploaded image is an astronomy / space related image
            such as nebula, galaxy, star cluster, planet, comet, moon, ISS, or telescope capture.
            
            Here is metadata information from NASA:
            $description
            
            1) Answer only "yes" or "no" on the first line.
            2) If yes, output 3~5 descriptive tags in a JSON array format.
            """.trimIndent()


        val requestBody = mapOf(
            "contents" to listOf(
                mapOf(
                    "parts" to listOf(
                        mapOf("text" to prompt),
                        mapOf("image_url" to imageUrl)
                    )
                )
            )
        )


        val responseBody = restClient.post()
            .uri(url)
            .body(requestBody)
            .retrieve()
            .body(Map::class.java) ?: return false to emptyList()

        val candidates = responseBody["candidates"] as? List<*> ?: return false to emptyList()
        val first = candidates.firstOrNull() as? Map<*, *> ?: return false to emptyList()
        val content = first["content"] as? Map<*, *> ?: return false to emptyList()
        val parts = content["parts"] as? List<*> ?: return false to emptyList()
        val text = (parts.firstOrNull() as? Map<*, *>)?.get("text")?.toString()?.trim() ?: ""

        val lines = text.split("\n")
        val isSpace = lines.firstOrNull()?.lowercase() == "yes"

        val tags = if (isSpace && lines.size > 1) {
            try {
                jacksonObjectMapper()
                    .readValue(lines[1], List::class.java)
                    .map { it.toString() }
            } catch (e: Exception) {
                emptyList()
            }
        } else emptyList()

        return isSpace to tags
    }
}