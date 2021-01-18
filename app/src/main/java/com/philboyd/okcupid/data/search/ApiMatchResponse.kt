package com.philboyd.okcupid.data.search

data class ApiMatchResponse (
    val total_matches : Int? = null,
    val data : List<ApiPerson>? = null,
    val paging : ApiPaging? = null
) {
    data class ApiPerson(
        val userid : String? = null,
        val username : String? = null,
        val age : Int? = null,
        val city_name : String? = null,
        val state_code : String? = null,
        val match : Int? = null,
        val liked : Boolean? = null,
        val photo: ApiPhoto? = null
    ) {
        data class ApiPhoto(
            val thumb_paths : ApiThumbPaths? = null
        ) {
            data class ApiThumbPaths(
                val large : String? = null
            )
        }
    }

    data class ApiPaging(
        val cursors : ApiCursors? = null
    ) {
        data class ApiCursors(
            val before : String? = null,
            val current : String? = null,
            val after : String? = null
        )
    }
}
