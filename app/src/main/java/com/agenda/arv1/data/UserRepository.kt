package com.agenda.arv1.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val databaseReference: DatabaseReference by lazy { Firebase.database.reference }
    private var currentRef: DatabaseReference? = null

    fun isUserLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun login(login: String, password: String): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(login, password).await()
    }

    fun logoff() {
        firebaseAuth.signOut()
    }

    suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    private fun currentQuery(): DatabaseReference {
        return currentRef ?: throw IllegalStateException("User has not been authenticated or is invalid.")
    }
}
