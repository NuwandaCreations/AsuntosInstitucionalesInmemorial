ckage com.example.asuntosinstitucionalesinmemorial.view.core.network.response

import com.google.gson.annotations.SerializedName

data class Material(
    @SerializedName("CANTIDAD") val cantidad: Int,
    @SerializedName("LOCALIZACIÓN") val localizacion: String,
    @SerializedName("OBJETO") val objeto: String,
    @SerializedName("OBSERVACIONES") val observaciones: String
)