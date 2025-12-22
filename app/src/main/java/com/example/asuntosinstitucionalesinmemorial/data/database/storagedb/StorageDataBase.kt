package com.example.asuntosinstitucionalesinmemorial.data.database.storagedb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.MaterialEntity
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.model.RegalosEntity

@Database(entities = [MaterialEntity::class, RegalosEntity::class], version = 1)
abstract class StorageDataBase : RoomDatabase() {
    abstract fun regalosDao(): RegalosDao
    abstract fun materialDao(): MaterialDao
}