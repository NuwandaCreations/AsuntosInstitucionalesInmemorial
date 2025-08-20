package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetMaterialStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetRegalosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteAllMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteAllMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteAllRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteAllRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.SetRegaloFirestoreUseCase
import org.koin.dsl.module

val domainModule = module {
    single { AddRegalosDBUseCase(get()) }
    single { GetRegalosDBUseCase(get()) }
    single { DeleteRegalosDBUseCase(get()) }
    single { DeleteAllRegalosDBUseCase(get()) }
    single { AddMaterialDBUseCase(get()) }
    single { GetMaterialDBUseCase(get()) }
    single { DeleteMaterialDBUseCase(get()) }
    single { DeleteAllMaterialDBUseCase(get()) }
    single { SetRegaloFirestoreUseCase(get()) }
    single { GetRegalosFirestoreUseCase(get()) }
    single { DeleteRegaloFirestoreUseCase(get()) }
    single { DeleteAllRegalosFirestoreUseCase(get()) }
    single { SetMaterialFirestoreUseCase(get()) }
    single { GetMaterialFirestoreUseCase(get()) }
    single { DeleteMaterialFirestoreUseCase(get()) }
    single { DeleteAllMaterialFirestoreUseCase(get()) }
    single { GetRegalosStorageUseCase(get()) }
    single { GetMaterialStorageUseCase(get()) }
    single { GetPhotosStorageUseCase() }
}