package com.vr.stringgeneratorcp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vr.stringgeneratorcp.model.RandomTextData
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: RandomTextData)

    @Delete
    suspend fun delete(data: RandomTextData)

    @Query("SELECT * FROM randomtextdata GROUP BY value ORDER BY id DESC")
    fun getAllStringData(): Flow<List<RandomTextData>>

    @Query("DELETE FROM randomtextdata WHERE id NOT IN (SELECT MIN(id) FROM randomtextdata GROUP BY value, length, created)")
    fun deleteDuplicateRecords()

    @Query("DELETE FROM randomtextdata")
    fun deleteAll()
}