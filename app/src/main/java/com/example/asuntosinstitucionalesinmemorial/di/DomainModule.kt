package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.DownloadStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.SetRegaloFirestoreUseCase
import org.koin.dsl.module

val domainModule = module {
    single { DownloadStorageUseCase(get()) }
    single { AddRegalosDBUseCase(get()) }
    single { GetRegalosDBUseCase(get()) }
    single { DeleteRegalosDBUseCase(get()) }
    single { AddMaterialDBUseCase(get()) }
    single { GetMaterialDBUseCase(get()) }
    single { DeleteMaterialDBUseCase(get()) }
    single { SetRegaloFirestoreUseCase(get()) }
    single { GetRegalosFirestoreUseCase(get()) }
    single { DeleteRegaloFirestoreUseCase(get()) }
    single { SetMaterialFirestoreUseCase(get()) }
    single { GetMaterialFirestoreUseCase(get()) }
    single { DeleteMaterialFirestoreUseCase(get()) }

}