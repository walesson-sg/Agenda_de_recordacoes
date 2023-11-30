package com.agenda.arv1.data.model

import com.google.android.gms.maps.model.LatLng

class PontoRecordacao constructor(
    var uid : String? = null,
    var nome : String? = null,
    var descricao : String? = null,
    var lat : Double? = null,
    var lng : Double? = null,
){
    fun toMap() = mutableMapOf(
        "uid" to uid,
        "nome" to nome,
        "descricao" to descricao,
        "lat" to lat.toString(),
        "lng" to lng.toString()
    )
}