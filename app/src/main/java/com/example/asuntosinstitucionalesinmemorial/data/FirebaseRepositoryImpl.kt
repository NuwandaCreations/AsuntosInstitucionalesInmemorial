package com.example.asuntosinstitucionalesinmemorial.data

import com.example.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.MATERIAL
import com.example.asuntosinstitucionalesinmemorial.util.Constants.Companion.REGALOS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseRepositoryImpl(
    val firebaseStorage: FirebaseStorage,
    val firestore: FirebaseFirestore
) : FirebaseRepository {
    override fun setStorageFirestore(storage: ProtocolStorage) {
        TODO("Not yet implemented")
    }

    override fun setRegaloFirestore(regalo: Regalos) {
        firestore.collection(REGALOS).document("${regalo.objeto}").set(regalo)

    }

    override fun setMaterialFirestore(material: Material) {
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

    override fun deleteRegalosFirestore(regalos: Regalos) {
        firestore.collection(REGALOS).document("${regalos.objeto}").delete()
    }

    override fun deleteMaterialFirestore(material: Material) {
        firestore.collection(MATERIAL).document("${material.objeto}").delete()
    }
}