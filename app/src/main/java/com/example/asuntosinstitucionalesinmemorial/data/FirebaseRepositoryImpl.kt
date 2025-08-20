package com.example.asuntosinstitucionalesinmemorial.data

import android.util.Log
import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.MATERIAL
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.MATERIAL_JSON
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.REGALOS
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.REGALOS_JSON
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl(
    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance(),
    val firestore: FirebaseFirestore
) : FirebaseRepository {
    override suspend fun getRegalosStorageJSON(): String {
        return try {
            val bytes =
                firebaseStorage.reference.child(REGALOS_JSON).getBytes(Long.MAX_VALUE).await()
            val jsonString = String(bytes)
            Log.i("Firebase", "JSON regalos descargado: $jsonString")
            jsonString
        } catch (e: Exception) {
            Log.e("Firebase", "Error descargando JSON", e)
            ""
        }
    }

    override suspend fun getMaterialStorageJSON(): String {
        return try {
            val bytes =
                firebaseStorage.reference.child(MATERIAL_JSON).getBytes(Long.MAX_VALUE).await()
            val jsonString = String(bytes)
            Log.i("Firebase", "JSON material descargado: $jsonString")
            jsonString
        } catch (e: Exception) {
            Log.e("Firebase", "Error descargando JSON", e)
            ""
        }
    }

    override suspend fun setRegaloFirestore(regalo: Regalos) {
        firestore.collection(REGALOS).document("${regalo.objeto}").set(regalo)
    }

    override suspend fun setMaterialFirestore(material: Material) {
        firestore.collection(MATERIAL).document("${material.objeto}").set(material)
    }

    override fun getRegalosFirestore(): Flow<List<Regalos>> {
        return firestore.collection(REGALOS)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(Regalos::class.java)
            }
    }

    override fun getMaterialFirestore(): Flow<List<Material>> {
        return firestore.collection(MATERIAL)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(Material::class.java)
            }
    }

    override suspend fun deleteRegalosFirestore(regalos: Regalos) {
        firestore.collection(REGALOS).document("${regalos.objeto}").delete()
    }

    override suspend fun deleteMaterialFirestore(material: Material) {
        firestore.collection(MATERIAL).document("${material.objeto}").delete()
    }

    override suspend fun deleteAllRegalosFirestore() {
        firestore.collection(REGALOS)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error eliminando regalos: ", exception)
            }
    }

    override suspend fun deleteAllMaterialFirestore() {
        firestore.collection(MATERIAL)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error eliminando material: ", exception)
            }
    }


}