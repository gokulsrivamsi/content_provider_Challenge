package com.vr.stringgeneratorcp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vr.stringgeneratorcp.model.RandomTextData
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Upsert
    suspend fun update(data: RandomTextData)

    @Delete
    suspend fun delete(data: RandomTextData)

    @Query("SELECT * FROM randomtextdata ORDER BY id DESC")
    fun getAllStringData(): Flow<List<RandomTextData>>

    @Query("DELETE FROM randomtextdata")
    fun deleteAll()
}