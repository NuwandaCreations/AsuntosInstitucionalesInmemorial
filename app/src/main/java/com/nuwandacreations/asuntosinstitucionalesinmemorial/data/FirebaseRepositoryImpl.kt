package com.nuwandacreations.asuntosinstitucionalesinmemorial.data

import android.util.Log
import com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.events.EventsResponse
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.FirebaseRepository
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Invitados
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.InvitadosRelevo
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Material
import com.nuwandacreations.asuntosinstitucionalesinmemorial.domain.model.Regalos
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENTOS
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.GUESTS
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.GUESTS_JSON
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.MATERIAL
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.MATERIAL_JSON
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.REGALOS
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.REGALOS_JSON
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.EVENTS_PHOTOS_JPG
import com.nuwandacreations.asuntosinstitucionalesinmemorial.util.Constants.Companion.GUESTS_PHOTOS_JPG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.text.Normalizer

class FirebaseRepositoryImpl(
    val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance(),
    val firestore: FirebaseFirestore
) : FirebaseRepository {
    override suspend fun getRegalosStorageJSON(): String {
        return try {
            val bytes =
                firebaseStorage.reference.child(REGALOS_JSON).getBytes(Long.MAX_VALUE).await()
            val jsonString = String(bytes)
            jsonString
        } catch (e: Exception) {
            Log.e("Firebase", "Error descargando JSON REGALOS", e)
            ""
        }
    }

    override suspend fun getMaterialStorageJSON(): String {
        return try {
            val bytes =
                firebaseStorage.reference.child(MATERIAL_JSON).getBytes(Long.MAX_VALUE).await()
            val jsonString = String(bytes)
            jsonString
        } catch (e: Exception) {
            Log.e("Firebase", "Error descargando JSON MATERIAL", e)
            ""
        }
    }

    override suspend fun getPhotosStorage(objeto: String): String {
        val url = firebaseStorage.reference.child("$objeto.jpg").downloadUrl.await()
        return url.toString()
    }

    override suspend fun getAllPhotosStorage(): ListResult {
        return firebaseStorage.reference.listAll().await()
    }

    override suspend fun getEventGuestsStorage(event: String): String {
        return try {
            val bytes =
                firebaseStorage.reference.child("$GUESTS_JSON$event.json").getBytes(Long.MAX_VALUE)
                    .await()
            val jsonString = String(bytes)
            jsonString
        } catch (e: Exception) {
            Log.e("Firebase", "Error descargando JSON INVITADOS", e)
            ""
        }
    }

    override suspend fun setRegaloFirestore(regalo: Regalos) {
        firestore.collection(REGALOS).document("${regalo.objeto}").set(regalo)
    }

    override suspend fun setMaterialFirestore(material: Material) {
        firestore.collection(MATERIAL).document("${material.objeto}").set(material)
    }

    override suspend fun setEventFirestore(event: EventsResponse) {
        firestore.collection(EVENTOS).document(event.id ?: "").set(event)
    }

    override suspend fun setGuestsFirestore(evento: String, invitado: Invitados) {
        firestore.collection(EVENTOS).document(evento).collection(GUESTS).document(invitado.nombre)
            .set(invitado)
    }

    override suspend fun setRelevoGuestsFirestore(evento: String, invitado: InvitadosRelevo) {
        firestore.collection(EVENTOS).document(evento).collection(GUESTS).document(invitado.nombre)
            .set(invitado)
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

    override fun getEventsFirestore(): Flow<List<EventsResponse>> {
        return firestore.collection(EVENTOS)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(EventsResponse::class.java)
            }
    }

    override fun getGuestsFirestore(evento: String): Flow<List<Invitados>> {
        return firestore.collection(EVENTOS).document(evento).collection(GUESTS)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(Invitados::class.java)
            }
    }

    override fun getRelevoGuestsFirestore(evento: String): Flow<List<InvitadosRelevo>> {
        return firestore.collection(EVENTOS).document(evento).collection(GUESTS)
            .snapshots()
            .map { snapshot ->
                snapshot.toObjects(InvitadosRelevo::class.java)
            }
    }

    override suspend fun getGuestsPhotosStorage(): List<Pair<String, String>> {
        val photos = firebaseStorage.reference.child(GUESTS_PHOTOS_JPG).listAll().await()
        var photosList: List<Pair<String, String>> = emptyList()
        photos.items.forEach {
            val upperName = it.name.substringBeforeLast(".").uppercase()
            val normalized = Normalizer.normalize(upperName, Normalizer.Form.NFD)
            val photoName = normalized.replace("\\p{Mn}+".toRegex(), "")
            photosList = photosList + Pair(photoName, it.downloadUrl.await().toString())
        }
        Tasks.whenAll()
        return photosList
    }

    override suspend fun getEventPhotoByIdStorage(id: String): String? {
        val photo = firebaseStorage.reference.child("$EVENTS_PHOTOS_JPG$id.jpg").downloadUrl.await()
        return photo.toString()
    }

    override fun getEventByIdFirestore(event: String): Flow<EventsResponse> {
        return firestore.collection(EVENTOS).document(event)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(EventsResponse::class.java) ?: EventsResponse()
            }
    }

    override fun getGuestByIdFirestore(
        evento: String,
        invitado: String
    ): Flow<Invitados> {
        return firestore.collection(EVENTOS).document(evento).collection(GUESTS).document(invitado)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(Invitados::class.java) ?: Invitados()
            }
    }

    override fun getRelevoGuestByIdFirestore(
        evento: String,
        invitado: String
    ): Flow<InvitadosRelevo> {
        return firestore.collection(EVENTOS).document(evento).collection(GUESTS).document(invitado)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(InvitadosRelevo::class.java) ?: InvitadosRelevo()
            }
    }

    override fun getRegaloByIdFirestore(regalo: String): Flow<Regalos> {
        return firestore.collection(REGALOS).document(regalo)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(Regalos::class.java) ?: Regalos()
            }
    }

    override fun getMaterialByIdFirestore(material: String): Flow<Material> {
        return firestore.collection(MATERIAL).document(material)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject(Material::class.java) ?: Material()
            }
    }

    override suspend fun deleteRegalosFirestore(regalos: Regalos) {
        firestore.collection(REGALOS).document("${regalos.objeto}").delete()
    }

    override suspend fun deleteMaterialFirestore(material: Material) {
        firestore.collection(MATERIAL).document("${material.objeto}").delete()
    }

    override suspend fun deleteEventFirestore(eventId: String) {
        firestore.collection(EVENTOS).document(eventId).delete()
    }

    override suspend fun updateRegalosFirestore(regalosList: List<Regalos>) {
        val collection = firestore.collection(REGALOS)
        val snapshot = collection.get().await()
        val deleteDocs = snapshot.documents.map { documentSnapshot ->
            collection.document(documentSnapshot.id).delete()
        }
        Tasks.whenAll(deleteDocs).await()

        regalosList.forEach {
            setRegaloFirestore(it)
        }
    }

    override suspend fun updateMaterialFirestore(materialList: List<Material>) {
        val collection = firestore.collection(MATERIAL)
        val snapshot = collection.get().await()
        val deleteDocs = snapshot.documents.map { documentSnapshot ->
            collection.document(documentSnapshot.id).delete()
        }
        Tasks.whenAll(deleteDocs).await()

        materialList.forEach {
            setMaterialFirestore(it)
        }
    }
}