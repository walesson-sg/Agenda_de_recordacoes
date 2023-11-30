package com.agenda.arv1.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.agenda.arv1.data.model.PontoRecordacao
import com.agenda.arv1.util.Constantes
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.agenda.arv1.util.FirebaseLiveData
import com.agenda.arv1.util.builders.MemoriaBuilder
import com.agenda.arv1.util.builders.MemoriasBuilder
import kotlinx.coroutines.tasks.await

class MemoriasRepository {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val database: DatabaseReference by lazy { Firebase.database.reference }
    private var currentRef: DatabaseReference? = null

    private val memorias: FirebaseLiveData<List<PontoRecordacao>> by lazy {
        return@lazy FirebaseLiveData(null, MemoriasBuilder())
    }

    init {
        updateInfoQuery(false)
        firebaseAuth.addAuthStateListener {
            updateInfoQuery()
        }
    }

    private fun updateInfoQuery(updateLiveDataQuery: Boolean = true) {
        firebaseAuth.currentUser?.let {
            Log.i("Stats", "Updating stats userId reference.")

            currentRef?.keepSynced(false)
            val ref = database
                .child(Constantes.MEMORIAS_PATH)
                .child(it.uid)
            ref.keepSynced(true)

            currentRef = ref
            if (updateLiveDataQuery) memorias.updateQuery(ref.orderByKey())
        }
    }

    fun getMemorias(): LiveData<List<PontoRecordacao>> {
        return memorias
    }

    fun getMemoria(uid: String): LiveData<PontoRecordacao?> {
        return FirebaseLiveData(currentQuery().child(uid), MemoriaBuilder())
    }

    fun insert(recordacao: PontoRecordacao): String? {
        val ref = currentQuery().push()
        val key = ref.key

        recordacao.uid = key
        val map = recordacao.toMap()

        ref.setValue(map)
        return key
    }

    fun update(memoria: PontoRecordacao) {
        currentQuery()
            .child(memoria.uid!!)
            .updateChildren(memoria.toMap().apply {
                remove("uid")
            } as Map<String, Any>)
    }

    fun delete(uid: String) {
        currentQuery()
            .child(uid)
            .removeValue()
    }
    private fun currentQuery(): DatabaseReference {
        return currentRef
            ?: throw IllegalStateException("User has not been authenticated or is invalid.")
    }
}
