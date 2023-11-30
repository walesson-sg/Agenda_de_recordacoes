package com.agenda.arv1.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.agenda.arv1.MarkerInfoAdapter
import com.agenda.arv1.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
//        mapFragment.getMapAsync { googleMap ->
//            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
//            onMapReady(googleMap)
//
//            googleMap.setOnMapLoadedCallback {
//                val bounds = LatLngBounds.builder()
//
////                pontosRecordacao.forEach {
////                    bounds.include(it.latLng)
////                }
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
//            }
//        }

    }

    //-4.9885319,-39.9284245
    override fun onMapReady(googleMap: GoogleMap) {
//        mGoogleMap = googleMap
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(boaViagem))
//        googleMap.addMarker(MarkerOptions().position(boaViagem).title("Boa Viagem - CE"))

//        pontosRecordacao.forEach { memoria ->
//            val marker = googleMap.addMarker(
//                MarkerOptions()
//                    .title(memoria.nome)
//                    .snippet(memoria.descricao)
//                    .position(memoria.latLng)
//                    .icon(
//                        BitmapHelper.vectorToBitmap(
//                            this, R.drawable.baseline_local_cafe_24,
//                            ContextCompat.getColor(
//                                this,
//                                androidx.appcompat.R.color.primary_material_dark
//                            )
//                        )
//                    )
//            )
//            marker?.tag = memoria
//        }

    }
}