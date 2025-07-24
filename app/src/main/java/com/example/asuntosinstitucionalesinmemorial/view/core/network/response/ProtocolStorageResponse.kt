package com.example.asuntosinstitucionalesinmemorial.view.core.network.response

import com.google.gson.annotations.SerializedName

data class ProtocolStorageResponse(
    @SerializedName("MATERIAL") val material: List<Material>,
    @SerializedName("REGALOS") val regalos: List<Regalos>
)