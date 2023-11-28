package com.agenda.arv1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.agenda.arv1.model.PontoRecordacao
import com.example.arv1.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoAdapter(private val context : Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker : Marker): View? = null

    override fun getInfoWindow(marker : Marker): View? {
        val memoria = marker.tag as? PontoRecordacao ?: return null

        val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_recordacao, null)

        view.findViewById<TextView>(R.id.txt_tittle).text = memoria.nome
        view.findViewById<TextView>(R.id.txt_description).text = memoria.descricao

        return view
    }
}