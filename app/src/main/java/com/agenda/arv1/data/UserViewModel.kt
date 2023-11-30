package com.agenda.arv1.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    fun isLogged(): Boolean = repository.isUserLogged()

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) { repository.login(email, password) }
    }

    suspend fun signUp(email: String, password: String) {
        withContext(Dispatchers.IO) {
            repository.signUp(email, password)
        }
    }

    suspend fun logoff() {
        withContext(Dispatchers.IO) {
            repository.logoff()
        }
    }

    class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown viewmodel")
        }
    }
}
