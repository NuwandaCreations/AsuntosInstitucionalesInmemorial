package com.example.asuntosinstitucionalesinmemorial.view.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drive.google.com/file/d/1mo2eh-QtGGMikSWp9E9s0hhAUK5GD2KJ/view?usp=drive_link/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}