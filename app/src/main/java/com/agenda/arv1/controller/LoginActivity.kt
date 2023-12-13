package com.agenda.arv1.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasViewModel
import com.agenda.arv1.data.UserViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var isLogging = false;
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.AuthViewModelFactory(
            (this.application as AgendaApplication).userRepository
        )
    }

    private val memoriasViewModel: MemoriasViewModel by viewModels {
        MemoriasViewModel.MemoriasViewModelFactory(
            (this.application as AgendaApplication).memoriasRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        btnLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            onLogin(username, password)
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        if (userViewModel.isLogged()) {

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onLogin(username: String, password: String) {
        if (!isLogging) {
            isLogging = true
            lifecycleScope.launch {
                try {
//                    lifecycleScope.launch {
//                        memoriasViewModel.create("Viagem com a Família", "Viagem de férias com a família", LatLng(-23.563518259448838, -46.63837987507322))
//                    }
                    userViewModel.login(username, password)
                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Credenciais inválidas!", Toast.LENGTH_SHORT).show()
                }
                isLogging = false
            }
        }
    }
}