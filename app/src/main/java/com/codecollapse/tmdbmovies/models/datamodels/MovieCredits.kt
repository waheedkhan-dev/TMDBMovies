package com.codecollapse.tmdbmovies.models.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.jetbrains.annotations.NotNull


data class MovieCredits(
    @Json(name = "id")
    var id : Int?=0,
    @Json(name = "cast")
    var cast : List<MovieCast> = arrayListOf(),
    @Json(name = "crew")
    var crew : List<MovieCast> = arrayListOf()
){
    @Entity(tableName = "movie_cast")
    data class MovieCast(
        var movieId : Int?=0,
        @Json(name = "adult")
        var adult : Boolean = false,
        @Json(name = "gender")
        var gender : Int ? =0,
        @NotNull
        @PrimaryKey
        @Json(name = "id")
        var id : Int?=0,
        @Json(name = "known_for_department")
        var known_for_department : String?="",
        @Json(name = "name")
        var name : String?="",
        @Json(name = "original_name")
        var original_name : String?="",
        @Json(name = "popularity")
        var popularity : Float?=0.0f,
        @Json(name = "profile_path")
        var profile_path : String?="",
        @Json(name = "cast_id")
        var cast_id : Int?=0,
        @Json(name = "character")
        var character : String?="",
        @Json(name = "credit_id")
        var credit_id : String?="",
        @Json(name = "order")
        var order : Int?=0
    ){}
}