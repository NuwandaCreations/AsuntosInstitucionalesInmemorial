package com.example.asuntosinstitucionalesinmemorial.data.network.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseService {
    fun getStorage(): String {
        return Firebase.storage.reference.storage.toString()
    }
}