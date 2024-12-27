package com.vr.stringgeneratorcp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vr.stringgeneratorcp.model.RandomTextData

@Database(entities = [RandomTextData::class], version = 1)
@TypeConverters(DataTypeConvertor::class)
abstract class StringsDataBase : RoomDatabase() {
    abstract fun RoomDao(): RoomDao
}