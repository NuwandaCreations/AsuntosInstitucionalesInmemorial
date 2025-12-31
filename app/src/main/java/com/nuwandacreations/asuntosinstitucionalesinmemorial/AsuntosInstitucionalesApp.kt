package com.nuwandacreations.asuntosinstitucionalesinmemorial

import android.app.Application
import com.nuwandacreations.asuntosinstitucionalesinmemorial.di.initKoin
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