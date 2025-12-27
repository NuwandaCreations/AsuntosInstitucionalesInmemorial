package com.nuwandacreations.asuntosinstitucionalesinmemorial.data.network.response.storage

import com.google.gson.annotations.SerializedName

data class RecordResponse(
    @SerializedName("MATERIAL") val material: List<MaterialResponse>,
    @SerializedName("REGALOS") val regalos: List<RegalosResponse>
)