package kr.ac.kumoh.s20220744.cosmos.dto

data class NasaApiResponse(
    val collection: NasaCollection
)

data class NasaCollection(
    val items: List<NasaItem>
)

data class NasaItem(
    val data: List<NasaData>,
    val links: List<NasaLink>?
)

data class NasaData(
    val nasa_id: String,
    val title: String?,
    val description: String?,
    val date_created: String?
)

data class NasaLink(
    val href: String,
    val rel: String,
    val render: String?
)
