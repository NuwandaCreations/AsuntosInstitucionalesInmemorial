package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import org.koin.dsl.module

val domainModule = module {
    single { DownloadStorageUseCase(get()) }
}