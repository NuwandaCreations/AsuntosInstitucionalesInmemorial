package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventGuestsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetEventsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.GetRelevoGuestsStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.SetGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.SetRelevoGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetAllPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetEventGuestsStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetMaterialStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.firestorageusecases.GetRegalosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases.DeleteAllMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases.GetMaterialByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebaseusecases.UpdateMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.roomusecases.UpdateMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.GetRegaloByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.SetRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebaseusecases.UpdateRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.DeleteAllRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.roomusecases.UpdateRegalosDBUseCase
import org.koin.dsl.module

val domainModule = module {
    single { AddRegalosDBUseCase(get()) }
    single { GetRegalosDBUseCase(get()) }
    single { DeleteRegalosDBUseCase(get()) }
    single { DeleteAllRegalosDBUseCase(get()) }
    single { UpdateRegalosDBUseCase(get()) }

    single { AddMaterialDBUseCase(get()) }
    single { GetMaterialDBUseCase(get()) }
    single { DeleteMaterialDBUseCase(get()) }
    single { DeleteAllMaterialDBUseCase(get()) }
    single { UpdateMaterialDBUseCase(get()) }

    single { SetRegaloFirestoreUseCase(get()) }
    single { GetRegalosFirestoreUseCase(get()) }
    single { GetRegaloByIdFirestoreUseCase(get()) }
    single { DeleteRegaloFirestoreUseCase(get()) }
    single { UpdateRegalosFirestoreUseCase(get()) }

    single { SetMaterialFirestoreUseCase(get()) }
    single { GetMaterialFirestoreUseCase(get()) }
    single { GetMaterialByIdFirestoreUseCase(get()) }
    single { DeleteMaterialFirestoreUseCase(get()) }
    single { UpdateMaterialFirestoreUseCase(get()) }

    single { GetEventsFirestoreUseCase(get()) }
    single { GetEventByIdFirestoreUseCase(get()) }
    single { GetEventGuestsFirestoreUseCase(get()) }
    single { SetGuestFirestoreUseCase(get()) }
    single { SetRelevoGuestFirestoreUseCase(get()) }

    single { GetRegalosStorageUseCase(get()) }
    single { GetMaterialStorageUseCase(get()) }
    single { GetPhotosStorageUseCase(get()) }
    single { GetAllPhotosStorageUseCase(get()) }
    single { GetEventGuestsStorageUseCase(get()) }
    single { GetRelevoGuestsStorageUseCase(get()) }
}