package com.codecollapse.tmdbmovies.models.datamodels

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.codecollapse.tmdbmovies.models.datasources.utils.converters.GenresTypeConverter
import com.squareup.moshi.Json
import org.jetbrains.annotations.NotNull


@Entity(tableName = "movie_detail")
data class MovieDetail(

    @PrimaryKey
    @NotNull
    @Json(name = "id")
    var id: Int? = 0,
    @Transient
    @Ignore
    var movieSubInfo: List<MovieSubInfo> = arrayListOf(),
    @Json(name = "adult")
    var adult: Boolean? = false,
    @Json(name = "backdrop_path")
    var backdrop_path: String? = "",
    @Ignore
    @Json(name = "belongs_to_collection")
    var belongs_to_collection: BelongToCollection? = null,
    @Json(name = "budget")
    var budget: Int? = 0,
    @Json(name = "genres")
    @TypeConverters(GenresTypeConverter::class)
    var genres: List<Genres> = arrayListOf(),
    @Json(name = "homepage")
    var homepage: String? = "",
    @Json(name = "imdb_id")
    var imdb_id: String? = "",
    @Json(name = "original_language")
    var original_language: String? = "",
    @Json(name = "original_title")
    var original_title: String? = "",
    @Json(name = "overview")
    var overview: String? = "",
    @Json(name = "popularity")
    var popularity: Float? = 0.0f,
    @Json(name = "poster_path")
    var poster_path: String? = "",
    @Json(name = "release_date")
    var release_date: String? = "",
    @Json(name = "revenue")
    var revenue: Int? = 0,
    @Json(name = "runtime")
    var runtime: Int? = 0,
    @Ignore
    @Json(name = "spoken_languages")
    var spoken_languages: List<Languages> = arrayListOf(),
    @Json(name = "status")
    var status: String? = "",
    @Json(name = "tagline")
    var tagline: String? = "",
    @Json(name = "title")
    var title: String? = "",
    @Json(name = "video")
    var video: Boolean? = false,
    @Json(name = "vote_average")
    var vote_average: Float? = 0.0f,
    @Json(name = "vote_count")
    var vote_count: Int? = 0

) {
    data class MovieSubInfo(
        var totalViewIcon: Int? = 0,
        var totalViews: String? = ""
    ) {}


    data class Genres(
        @Json(name = "id")
        var id: Int? = 0,
        @Json(name = "name")
        var name: String? = ""
    ) {}


    data class Languages(

        @Json(name = "english_name")
        var english_name: String? = "",
        @Json(name = "iso_639_1")
        var iso_639_1: String? = "",
        @Json(name = "name")
        var name: String? = ""
    ) {}


    data class BelongToCollection(
        @Json(name = "id")
        var id: Int? = 0,
        @Json(name = "name")
        var name: String? = "",
        @Json(name = "poster_path")
        var poster_path: String? = "",
        @Json(name = "backdrop_path")
        var backdrop_path: String? = ""
    ) {}
}
