package com.agenda.arv1.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.agenda.arv1.data.model.PontoRecordacao
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoriasViewModel(private val repository: MemoriasRepository) : ViewModel(){
    private val memoriasData: LiveData<List<PontoRecordacao>> by lazy {
        repository.getMemorias()
    }
    private val cache: MutableMap<String, LiveData<PontoRecordacao?>> by lazy {
        mutableMapOf()
    }

    fun getMemorias(): LiveData<List<PontoRecordacao>> {
        return memoriasData
    }

    fun getMemoria(uid: String): LiveData<PontoRecordacao?> {
        return cache[uid] ?: repository.getMemoria(uid).also { cache[uid] = it }
    }

    suspend fun create(nome: String, descricao: String, latLng: LatLng): String? {
        return withContext(Dispatchers.IO) {
            repository.insert(PontoRecordacao(null, nome, descricao, latLng.latitude, latLng.longitude))
        }
    }

    suspend fun remove(uid: String) {
        withContext(Dispatchers.IO) {
            repository.delete(uid)
        }
    }

    suspend fun update(uid: String, nome: String, descricao: String, latLng: LatLng) {
        withContext(Dispatchers.IO) {
            repository.update(PontoRecordacao(uid, nome, descricao, latLng.latitude, latLng.longitude))
        }
    }

    suspend fun updateStat(recordacao: PontoRecordacao) {
        withContext(Dispatchers.IO) {
            repository.update(recordacao)
        }
    }

    class MemoriasViewModelFactory(private val repository: MemoriasRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MemoriasViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MemoriasViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown viewmodel")
        }
    }
}