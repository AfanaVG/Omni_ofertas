package com.example.omni_ofertas.ui.model

import com.google.type.LatLng
import java.io.Serializable

data class Oferta (

        val id :String,
        var nombre :String,
        val descripcion :String?,
        val foto :String?,
        val expiracion: Int?,
        val localizacion: LatLng?,
        val autor: Int?


):Serializable