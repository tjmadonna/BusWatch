package com.madonnaapps.buswatch.cache.converter

import androidx.room.TypeConverter

internal object RouteTypeConverter {

    private const val DELIMITER = "|"

    @JvmStatic
    @TypeConverter
    fun toRouteStringList(routeString: String): List<String> = routeString.split(DELIMITER)

    @JvmStatic
    @TypeConverter
    fun fromRouteStringList(routeList: List<String>): String = routeList.joinToString(DELIMITER)

}