package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.data.RepositoryImpl
import com.example.asuntosinstitucionalesinmemorial.data.network.drive.DriveService
import com.example.asuntosinstitucionalesinmemorial.domain.Repository
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.BASE_URL
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideRetrofit() }
    single { DriveService(get()) }
    single <Repository> { RepositoryImpl(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}