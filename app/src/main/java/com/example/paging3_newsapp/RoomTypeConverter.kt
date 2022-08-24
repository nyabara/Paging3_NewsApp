package com.example.paging3_newsapp

import androidx.room.TypeConverter
import com.example.paging3_newsapp.model.Source
import com.google.gson.Gson

class RoomTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun stringToSource(str: String): Source {
        return Gson().fromJson(str, Source::class.java)
    }

}