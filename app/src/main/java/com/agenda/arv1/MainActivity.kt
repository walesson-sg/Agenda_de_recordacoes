package com.example.arv1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.agenda.arv1.BitmapHelper
import com.agenda.arv1.PontoRecordacao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    var boaViagem = LatLng(-5.1260677305366755, -39.7310485609616)
    var rioBV = LatLng(-5.119807554904492, -39.73120742055659)
    var autoVip = LatLng(-7.0209901,-37.2717041)
    private val pontosRecordacao = arrayListOf(
        PontoRecordacao(1, "BV", "BOA VIAGEM - CE", boaViagem),
        PontoRecordacao(2, "Forum", "BOA VIAGEM - CE", rioBV),
        PontoRecordacao(3, "AutoVIP", "PARAIBA - CE", autoVip)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync{googleMap ->
            onMapReady(googleMap)
        }

    }
//-4.9885319,-39.9284245
    override fun onMapReady(googleMap: GoogleMap) {
//        mGoogleMap = googleMap
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(boaViagem))
//        googleMap.addMarker(MarkerOptions().position(boaViagem).title("Boa Viagem - CE"))

    pontosRecordacao.forEach{memoria->
        val marker = googleMap.addMarker(
            MarkerOptions()
                .title(memoria.nome)
                .snippet(memoria.descricao)
                .position(memoria.latLng)
                .icon(BitmapHelper.vectorToBitmap(
                    this, R.drawable.baseline_local_cafe_24,
                    ContextCompat.getColor(this, androidx.appcompat.R.color.primary_material_dark)))
        )
    }

    }
}