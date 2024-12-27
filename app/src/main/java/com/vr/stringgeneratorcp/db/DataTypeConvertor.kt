package com.vr.stringgeneratorcp.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vr.stringgeneratorcp.model.RandomTextData

class DataTypeConvertor {
    @TypeConverter
    fun stringItemsList(json: String?): List<RandomTextData>? {
        val type = object : TypeToken<List<RandomTextData>>() {}.type
        return if (json != null) {
            Gson().fromJson(json, type)
        } else {
            null
        }
    }
}