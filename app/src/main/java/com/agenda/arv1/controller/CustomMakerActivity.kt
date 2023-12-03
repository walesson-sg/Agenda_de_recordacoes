package com.agenda.arv1.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.agenda.arv1.R
import kotlinx.coroutines.launch

class CustomMakerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_marker_recordacao)
        val btnMemoria: Button = findViewById(R.id.btnMemorias)
        val teste = findViewById<TextView>(R.id.textTeste)

        lifecycleScope.launch {
            btnMemoria.setOnClickListener{
                val intent = Intent(applicationContext, MemoriaActivity::class.java)
                startActivity(intent)
            }
        }

    }
}