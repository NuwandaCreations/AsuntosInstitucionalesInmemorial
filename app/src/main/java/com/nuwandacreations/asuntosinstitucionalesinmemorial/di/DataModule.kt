package com.nuwandacreations.asuntosinstitucionalesinmemorial.di

import android.content.Context
import androidx.room.Room
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.FirebaseRepositoryImpl
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.RepositoryImpl
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.database.storagedb.StorageDataBase
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.Repository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.BASE_URL
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideRetrofit() }
    single { provideStorageDataBase(get()) }
    single { get<StorageDataBase>().materialDao() }
    single { get<StorageDataBase>().regalosDao() }
    single<Repository> { RepositoryImpl(get(), get()) }
    single<FirebaseRepository> {
        FirebaseRepositoryImpl(
            provideFirebaseStorage(),
            provideFirestore()
        )
    }
}

fun provideRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideStorageDataBase(appContext: Context): StorageDataBase {
    return Room.databaseBuilder(
        appContext,
        StorageDataBase::class.java,
        "StorageDataBase"
    ).build()
}

fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

fun provideFirestore(): FirebaseFirestore = Firebase.firestore