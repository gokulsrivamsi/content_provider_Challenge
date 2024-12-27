package com.vr.stringgeneratorcp.repository

import com.vr.stringgeneratorcp.db.StringsDataBase
import com.vr.stringgeneratorcp.model.RandomTextData
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val db: StringsDataBase) {

    suspend fun update(data: RandomTextData) {
        db.RoomDao().update(data)
    }

    suspend fun delete(data: RandomTextData) {
        db.RoomDao().delete(data)
    }

    fun getAllStringData(): Flow<List<RandomTextData>> {
        return db.RoomDao().getAllStringData()
    }

    fun deleteAll() {
        db.RoomDao().deleteAll()
    }
}