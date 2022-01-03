package com.codecollapse.tmdbmovies.models.datamodels


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.jetbrains.annotations.NotNull


data class TMDBMovies(
    @Json(name = "page")
    var page: Int? = 0,
    @Json(name = "results")
    var results: List<Results>,
    @Json(name = "total_pages")
    var total_pages: Int? = 0,
    @Json(name = "total_results")
    var total_results: Int? = 0,
    @Json(name = "status_code")
    var status_code: Int? = 0,
    @Json(name = "status_message")
    var status_message: String? = "",
    @Json(name = "success")
    var success: Boolean? = false
) {

    @Entity(tableName = "movies")
    data class Results(
        @NotNull
        @PrimaryKey
        @Json(name = "id")
        var id: Int? = 0,
        @Json(name = "adult")
        var adult: Boolean? = false,
        @Json(name = "backdrop_path")
        var backdrop_path: String? = "",
        @Json(name = "original_language")
        var original_language: String? = "",
        @Json(name = "original_title")
        var original_title: String? = "",
        @Json(name = "poster_path")
        var poster_path: String? = "",
        @Json(name = "video")
        var video: Boolean? = false,
        @Json(name = "vote_average")
        var vote_average: Float? = 0f,
        @Json(name = "overview")
        var overview: String? = "",
        @Json(name = "release_date")
        var release_date: String? = "",
        @Json(name = "vote_count")
        var vote_count: Int? = 0,
        @Json(name = "title")
        var title: String? = "",
        @Json(name = "popularity")
        var popularity: Float? = 0f,
        @Json(name = "media_type")
        var media_type: String? = "",
        var movieType : String?=""
    ) {}
}
