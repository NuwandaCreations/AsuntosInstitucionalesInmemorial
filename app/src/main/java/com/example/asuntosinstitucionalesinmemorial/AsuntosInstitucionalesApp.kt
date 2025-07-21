package com.example.asuntosinstitucionalesinmemorial

import android.app.Application
import com.example.asuntosinstitucionalesinmemorial.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AsuntosInstitucionalesApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AsuntosInstitucionalesApp)
            androidLogger()
        }
    }
}