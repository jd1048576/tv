package jdr.tv.data.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import jdr.tv.data.local.entities.Cast
import jdr.tv.data.local.entities.ContentRating
import jdr.tv.data.local.entities.CreatedBy
import jdr.tv.data.local.entities.Crew
import jdr.tv.data.local.entities.Network
import jdr.tv.data.local.entities.ProductionCompany
import jdr.tv.data.local.entities.Video

object ListTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromJsonToStringList(json: String): List<String> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromStringList(list: List<String>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToCastList(json: String): List<Cast> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromCastList(list: List<Cast>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToContentRatingList(json: String): List<ContentRating> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromContentRatingList(list: List<ContentRating>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToCreatedByList(json: String): List<CreatedBy> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromCreatedByList(list: List<CreatedBy>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToCrewList(json: String): List<Crew> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromCrewList(list: List<Crew>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToNetworkList(json: String): List<Network> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromNetworkList(list: List<Network>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToProductionCompanyList(json: String): List<ProductionCompany> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromProductionCompanyList(list: List<ProductionCompany>): String {
        return toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromJsonToVideoList(json: String): List<Video> {
        return fromJson(json)
    }

    @TypeConverter
    @JvmStatic
    fun toJsonFromVideoList(list: List<Video>): String {
        return toJson(list)
    }

    private inline fun <reified T> fromJson(json: String): List<T> {
        try {
            return moshi.adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(json)!!
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }
    }

    private inline fun <reified T> toJson(list: List<T>): String {
        return moshi.adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).toJson(list)
    }

    private val moshi: Moshi = Moshi.Builder().build()
}
