package com.example.asuntosinstitucionalesinmemorial.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class StorageService {
    fun getStorage(): String {
        return Firebase.storage.reference.storage.toString()
    }
}