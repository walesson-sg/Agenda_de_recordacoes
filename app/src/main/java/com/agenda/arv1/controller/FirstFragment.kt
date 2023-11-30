package com.agenda.arv1.controller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasViewModel
import com.agenda.arv1.databinding.FragmentFirstBinding
import com.agenda.arv1.util.adapters.CustomAdapter
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private val viewModel: MemoriasViewModel by activityViewModels {
        MemoriasViewModel.MemoriasViewModelFactory(
            (activity?.application as AgendaApplication).memoriasRepository
        )
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        val adapter = CustomAdapter()
        adapter.itemTouchedCallback = {
            // TODO
        }

        binding.memoriaLista.adapter = adapter
        binding.memoriaLista.layoutManager = LinearLayoutManager(activity)

        viewModel.getMemorias().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitList(it.reversed())
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}