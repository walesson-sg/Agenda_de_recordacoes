package com.agenda.arv1.controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.MarkerInfoAdapter
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasViewModel
import com.agenda.arv1.data.model.PontoRecordacao
import com.agenda.arv1.databinding.FragmentSecondBinding
import com.agenda.arv1.util.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val viewModel: MemoriasViewModel by activityViewModels {
        MemoriasViewModel.MemoriasViewModelFactory(
            (activity?.application as AgendaApplication).memoriasRepository
        )
    }

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private var mGoogleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mGoogleMap?.setOnMapClickListener { this }
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(requireContext()))
            googleMap.setOnMapLoadedCallback {
                viewModel.getMemorias().observe(requireActivity()) { recordacoes ->
                    mGoogleMap?.setOnMapLongClickListener {
                        val dialogAddRecordacao = AlertDialog.Builder(this.context)
                        dialogAddRecordacao.setTitle("Nova Memoria")
                        dialogAddRecordacao.setMessage("Adicionar nova Memoria?")
                        dialogAddRecordacao.setPositiveButton("Sim", DialogInterface.OnClickListener { ok, which ->  addRecordacao(it)})
                        dialogAddRecordacao.setNegativeButton("Não", null)

                        dialogAddRecordacao.create().show()
                        mGoogleMap?.addMarker(MarkerOptions().position(it))
                    }
                    updatePontos(googleMap, recordacoes)
                    val bounds = LatLngBounds.builder()
                    recordacoes.forEach {
                        if (it.lat != null && it.lng != null) {
                            bounds.include(LatLng(it.lat!!, it.lng!!))
                        }
                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addRecordacao(latLng: LatLng?){
        requireActivity().lifecycleScope.launch {
            val intent = Intent(requireContext(), AddRecordacaoActivity::class.java)
            intent.putExtra("latLng", latLng)
            startActivity(intent)
        }
    }
    fun onMapClick(latLng: LatLng?) {
        val marker = MarkerOptions()
            .position(latLng!!)
        this.mGoogleMap?.addMarker(marker)
    }

    fun updatePontos(googleMap: GoogleMap, recordacoes: List<PontoRecordacao>) {
        mGoogleMap = googleMap

        recordacoes.forEach { memoria ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(memoria.nome)
                    .snippet(memoria.descricao)
                    .position(LatLng(memoria.lat!!, memoria.lng!!))
                    .icon(
                        BitmapHelper.vectorToBitmap(
                            requireContext(), R.drawable.baseline_local_cafe_24,
                            ContextCompat.getColor(
                                requireContext(),
                                androidx.appcompat.R.color.primary_material_dark
                            )
                        )
                    )
            )
            marker?.tag = memoria
        }

    }
}