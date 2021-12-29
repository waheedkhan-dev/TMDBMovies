package com.codecollapse.tmdbmovies.models.datasources.utils.converters

import androidx.annotation.Nullable
import androidx.room.TypeConverter
import com.codecollapse.tmdbmovies.models.datamodels.MovieDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class GenresTypeConverter {

    @TypeConverter
    fun genresToString(list: List<MovieDetail.Genres?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToGenresList(@Nullable data: String?): List<MovieDetail.Genres?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<MovieDetail.Genres?>?>() {}.type
        val gson = Gson()
        return gson.fromJson<List<MovieDetail.Genres?>>(data, listType)

    }
}