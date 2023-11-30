package com.agenda.arv1.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.R
import com.agenda.arv1.data.UserViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private var isRegistering = false
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.AuthViewModelFactory(
            (this.application as AgendaApplication).userRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextNewUsername = findViewById<EditText>(R.id.editTextNewUsername)
        val editTextNewPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            if (isRegistering) {
                return@setOnClickListener
            }

            isRegistering = true
            val newUsername = editTextNewUsername.text.toString()
            val newPassword = editTextNewPassword.text.toString()

            if (newUsername.isEmpty()) {
                Toast.makeText(this.applicationContext, "E-mail inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.isEmpty()) {
                Toast.makeText(this.applicationContext, "Senha inválida.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                var toastMessage = "";
                try {
                    userViewModel.signUp(newUsername, newPassword)

                    Toast.makeText(applicationContext, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: FirebaseAuthWeakPasswordException) {
                    toastMessage = "Senha fraca, deve ter no mínimo 6 caracteres."
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    toastMessage = "E-mail inválido."
                } catch (e: FirebaseAuthUserCollisionException) {
                    toastMessage = "Usuário já existe."
                } catch (e: Exception) {
                    Log.e("SignUpError", e.message!!)
                    toastMessage = "Erro desconhecido"
                }

                if (toastMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
                }

                isRegistering = false
            }
        }
    }

}
