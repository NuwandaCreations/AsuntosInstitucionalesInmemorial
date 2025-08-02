package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegalosUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosUseCase
import org.koin.dsl.module

val domainModule = module {
    single { DownloadStorageUseCase(get()) }
    single { AddRegalosUseCase(get()) }
    single { GetRegalosUseCase(get()) }
    single { DeleteRegalosUseCase(get()) }
    single { AddMaterialUseCase(get()) }
    single { GetMaterialUseCase(get()) }
    single { DeleteMaterialUseCase(get()) }
}