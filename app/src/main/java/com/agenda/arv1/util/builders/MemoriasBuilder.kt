package com.agenda.arv1.util.builders

import com.agenda.arv1.data.model.PontoRecordacao
import com.agenda.arv1.util.FirebaseLiveData
import com.google.firebase.database.DataSnapshot

class MemoriasBuilder : FirebaseLiveData.DataBuilder<List<PontoRecordacao>>{
    companion object {
        private val statBuilder by lazy { MemoriaBuilder() }
    }

    override fun buildData(dataSnapshot: DataSnapshot): List<PontoRecordacao> {
        val list: MutableList<PontoRecordacao> = mutableListOf()
        for (ds in dataSnapshot.children) {
            val pr = statBuilder.buildData(ds)
            if (pr != null) {
                list.add(pr)
            }
        }

        return list
    }
}