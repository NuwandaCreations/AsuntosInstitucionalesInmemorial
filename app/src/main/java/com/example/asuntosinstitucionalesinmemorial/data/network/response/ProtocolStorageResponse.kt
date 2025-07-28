package com.example.asuntosinstitucionalesinmemorial.data.network.response

import com.example.asuntosinstitucionalesinmemorial.domain.model.Material
import com.example.asuntosinstitucionalesinmemorial.domain.model.ProtocolStorage
import com.example.asuntosinstitucionalesinmemorial.domain.model.Regalos

data class ProtocolStorageResponse(
    val id: String,
    val metadata: MetadataResponse,
    val record: RecordResponse
)

fun ProtocolStorageResponse.toDomain(): ProtocolStorage {
    val materialList = mutableListOf<Material>()
    record.material.forEach {
        materialList.add(it.toDomain())
    }
    val regalosList = mutableListOf<Regalos>()
    record.regalos.forEach {
        regalosList.add(it.toDomain())
    }
    return ProtocolStorage(
        material = materialList,
        regalos = regalosList
    )
}