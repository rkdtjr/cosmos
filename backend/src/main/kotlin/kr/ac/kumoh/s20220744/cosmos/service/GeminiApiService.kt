package kr.ac.kumoh.s20220744.cosmos.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.genai.Client
import com.google.genai.types.GenerateContentResponse
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
        val (isSpace, tags) = analyzeSpaceImage(imageUrl, description)

        val image = spaceImageRepository.findById(nasaId).orElse(null) ?: return

        val updated = image.copy(
            tags = tags,
            status = if (isSpace) "DONE" else "FAILED"
        )

        spaceImageRepository.save(updated)
    }
    fun analyzeSpaceImage(imageUrl: String?, description: String?): Pair<Boolean, List<String>> {
        if (imageUrl.isNullOrBlank()) return false to emptyList()

        val client = Client
            .builder()
            .apiKey(apiKey)
            .build()

        val prompt = """
        You are an astronomy expert.
        Determine whether the image from the following URL is an astronomy or space-related image
        such as nebula, galaxy, star cluster, planet, comet, moon, ISS, or telescope capture.

        NASA Metadata:
        $description

        Image URL:
        $imageUrl

        Output Format:
        1) First line: "yes" or "no" only.
        2) Second line: If yes, output 3~5 descriptive tags in a JSON array format.
           Example: ["nebula", "infrared", "Hubble", "star formation"]
    """.trimIndent()

        // --------- 요청 제한 + 자동 재시도 Backoff ---------
        var delayMs = 1000L
        repeat(5) { attempt ->
            try {
                val response: GenerateContentResponse = client.models.generateContent(
                    "gemini-2.5-flash-lite",
                    prompt,
                    /* safety settings */ null
                )

                val raw = response.text()?.trim() ?: ""
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

                return isSpace to tags

            } catch (e: Exception) {
                if (e.message?.contains("429") == true) {
                    println("⚠️ Rate limit — retry in $delayMs ms (attempt ${attempt + 1}/5)")
                    Thread.sleep(delayMs)
                    delayMs *= 2
                } else {
                    println("❌ Unexpected error: $e")
                    return false to emptyList()
                }
            }
        }

        return false to emptyList()
    }

}