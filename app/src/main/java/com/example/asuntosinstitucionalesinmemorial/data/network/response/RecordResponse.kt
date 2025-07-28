package com.example.asuntosinstitucionalesinmemorial.data.network.response

import com.google.gson.annotations.SerializedName

data class RecordResponse(
    @SerializedName("MATERIAL") val material: List<MaterialResponse>,
    @SerializedName("REGALOS") val regalos: List<RegalosResponse>
)