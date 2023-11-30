package com.agenda.arv1.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.MarkerInfoAdapter
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasViewModel
import com.agenda.arv1.data.model.PontoRecordacao
import com.agenda.arv1.databinding.FragmentSecondBinding
import com.agenda.arv1.util.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

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

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(requireContext()))
            googleMap.setOnMapLoadedCallback {
                viewModel.getMemorias().observe(requireActivity()) { recordacoes ->
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