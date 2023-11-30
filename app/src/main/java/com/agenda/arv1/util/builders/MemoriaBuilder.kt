package com.agenda.arv1.util.builders

import com.agenda.arv1.data.model.PontoRecordacao
import com.agenda.arv1.util.FirebaseLiveData
import com.google.firebase.database.DataSnapshot

class MemoriaBuilder : FirebaseLiveData.DataBuilder<PontoRecordacao?>{
    override fun buildData(dataSnapshot: DataSnapshot): PontoRecordacao? {
        val dataValue = dataSnapshot.value as Map<*, *>?

        return if (dataValue != null) {
            PontoRecordacao(
                uid = dataValue["uid"] as String?,
                nome = dataValue["nome"] as String?,
                descricao = dataValue["descricao"] as String?,
                lat = (dataValue["lat"] as String?)?.toDouble(),
                lng = (dataValue["lng"] as String?)?.toDouble(),
            )
        } else null
    }
}