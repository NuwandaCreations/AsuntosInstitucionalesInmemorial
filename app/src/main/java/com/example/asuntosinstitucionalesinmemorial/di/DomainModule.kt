package com.example.asuntosinstitucionalesinmemorial.di

import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventGuestsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetEventsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetRelevoGuestsStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.SetRelevoGuestFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetAllPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firebasestorage.GetEventGuestsStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.eventsusecases.firestore.GetRelevoGuestsFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firebasestorage.GetMaterialStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetPhotosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firebasestorage.GetRegalosStorageUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.DeleteMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.GetMaterialByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.GetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.SetMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.firestore.UpdateMaterialFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.AddMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.DeleteAllMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.DeleteMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.GetMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.materialusecases.room.UpdateMaterialDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.DeleteRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.GetRegaloByIdFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.GetRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.SetRegaloFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.firestore.UpdateRegalosFirestoreUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.AddRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.DeleteAllRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.DeleteRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.GetRegalosDBUseCase
import com.example.asuntosinstitucionalesinmemorial.domain.usecases.regalosusecases.room.UpdateRegalosDBUseCase
import org.koin.dsl.module

val domainModule = module {

    single { AddRegalosDBUseCase(get()) }
    single { GetRegalosDBUseCase(get()) }
    single { DeleteRegalosDBUseCase(get()) }
    single { DeleteAllRegalosDBUseCase(get()) }
    single { UpdateRegalosDBUseCase(get()) }

    single { SetRegaloFirestoreUseCase(get()) }
    single { GetRegalosFirestoreUseCase(get()) }
    single { GetRegaloByIdFirestoreUseCase(get()) }
    single { DeleteRegaloFirestoreUseCase(get()) }
    single { UpdateRegalosFirestoreUseCase(get()) }

    single { AddMaterialDBUseCase(get()) }
    single { GetMaterialDBUseCase(get()) }
    single { DeleteMaterialDBUseCase(get()) }
    single { DeleteAllMaterialDBUseCase(get()) }
    single { UpdateMaterialDBUseCase(get()) }

    single { SetMaterialFirestoreUseCase(get()) }
    single { GetMaterialFirestoreUseCase(get()) }
    single { GetMaterialByIdFirestoreUseCase(get()) }
    single { DeleteMaterialFirestoreUseCase(get()) }
    single { UpdateMaterialFirestoreUseCase(get()) }
    //STORAGE USE CASES
    single { GetRegalosStorageUseCase(get()) }
    single { GetMaterialStorageUseCase(get()) }
    single { GetPhotosStorageUseCase(get()) }
    single { GetAllPhotosStorageUseCase(get()) }
    single { GetEventGuestsStorageUseCase(get()) }
    single { GetRelevoGuestsStorageUseCase(get()) }

    single { GetEventsFirestoreUseCase(get()) }
    single { GetEventByIdFirestoreUseCase(get()) }
    single { GetEventGuestsFirestoreUseCase(get()) }
    single { GetRelevoGuestsFirestoreUseCase(get()) }
    single { SetGuestFirestoreUseCase(get()) }
    single { SetRelevoGuestFirestoreUseCase(get()) }
}