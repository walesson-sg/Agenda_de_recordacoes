package com.agenda.arv1.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class AddRecordacaoActivity : AppCompatActivity() {

    private val memoriasViewModel: MemoriasViewModel by viewModels {
        MemoriasViewModel.MemoriasViewModelFactory(
            (this.application as AgendaApplication).memoriasRepository
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addrecord)
        val latLng: LatLng? = intent.getParcelableExtra("latLng")
        val btnAddMemoria = findViewById<Button>(R.id.btnAddRecord)
        val nome: EditText = findViewById(R.id.textNome)
        val descricao = findViewById<EditText>(R.id.textDescricao)

        btnAddMemoria.setOnClickListener {
            if (latLng != null) {
                novaMemoria(nome.text.toString(), descricao.text.toString(), LatLng(latLng.latitude, latLng.longitude))
            }
            lifecycleScope.launch {
                val intent = Intent(null, SecondFragment::class.java)
                startActivity(intent)
            }

        }
    }

    fun novaMemoria(nome: String, descricao: String, latLng: LatLng){
        lifecycleScope.launch {
            memoriasViewModel.create(nome, descricao, latLng)
        }
    }
}