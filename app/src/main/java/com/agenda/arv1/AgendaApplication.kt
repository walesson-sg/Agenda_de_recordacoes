package com.agenda.arv1

import android.app.Application
import com.agenda.arv1.data.MemoriasRepository
import com.agenda.arv1.data.UserRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AgendaApplication : Application() {

    val userRepository: UserRepository by lazy {
        enablePersistence()
        UserRepository()
    }

    val memoriasRepository: MemoriasRepository by lazy {
        enablePersistence()
        MemoriasRepository()
    }

    private var persistenceEnabled = false

    private fun enablePersistence() {
        if (!persistenceEnabled) {
            Firebase.database.setPersistenceEnabled(true)
            persistenceEnabled = true
        }
    }

}