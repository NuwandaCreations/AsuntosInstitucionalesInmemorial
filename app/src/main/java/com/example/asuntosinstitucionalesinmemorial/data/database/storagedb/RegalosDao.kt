package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegalosDao {
    @Query("SELECT * FROM RegalosEntity")
    fun getAllRegalos(): Flow<List<RegalosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRegalos(vararg regalos: RegalosEntity)

    @Delete
    suspend fun deleteRegalos(vararg regalos: RegalosEntity)
}