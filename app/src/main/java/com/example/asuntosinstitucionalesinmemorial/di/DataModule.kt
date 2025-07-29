package com.example.asuntosinstitucionalesinmemorial.di

import android.content.Context
import androidx.room.Room
import com.example.asuntosinstitucionalesinmemorial.data.RepositoryImpl
import com.example.asuntosinstitucionalesinmemorial.data.database.storagedb.StorageDataBase
import com.example.asuntosinstitucionalesinmemorial.data.network.drive.DriveService
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideRetrofit() }
    single { provideStorageDataBase(get()) }
    single { get<StorageDataBase>().materialDao() }
    single { get<StorageDataBase>().regalosDao() }
    single { DriveService(get()) }
    single<Repository> { RepositoryImpl(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideStorageDataBase(appContext: Context): StorageDataBase {
    return Room.databaseBuilder(
        appContext,
        StorageDataBase::class.java,
        "StorageDataBase"
    ).build()
}