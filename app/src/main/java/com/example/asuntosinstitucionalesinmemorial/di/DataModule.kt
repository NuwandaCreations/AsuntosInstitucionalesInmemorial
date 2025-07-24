package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.BASE_URL
import com.example.asuntosinstitucionalesinmemorial.view.core.network.GoogleDriveService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideRetrofit() }
    single { GoogleDriveService(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}