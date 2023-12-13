package com.agenda.arv1.controller

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agenda.arv1.AgendaApplication
import com.agenda.arv1.R
import com.agenda.arv1.data.MemoriasRepository
import com.agenda.arv1.data.MemoriasViewModel
import com.agenda.arv1.data.UserRepository
import com.agenda.arv1.data.UserViewModel
import com.agenda.arv1.databinding.FragmentFirstBinding
import com.agenda.arv1.util.adapters.CustomAdapter
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private val viewModel: MemoriasViewModel by activityViewModels {
        MemoriasViewModel.MemoriasViewModelFactory(
            (requireActivity().application as AgendaApplication).memoriasRepository
        )
    }

    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModel.AuthViewModelFactory(
            (requireActivity().application as AgendaApplication).userRepository
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

        val adapter = CustomAdapter(
            itemDeleteCallback = { item ->
                val dialogAddRecordacao = AlertDialog.Builder(context)
                dialogAddRecordacao.setTitle("Deletar recordação")

                dialogAddRecordacao.setMessage("Você quer deletar esta recordação?")
                dialogAddRecordacao.setPositiveButton("Sim", DialogInterface.OnClickListener { ok, which ->
                    item.uid?.let { uid ->
                        requireActivity().lifecycleScope.launch {
                            viewModel.remove(uid)
                        }
                    }
                })
                dialogAddRecordacao.setNegativeButton("Não", null)
                dialogAddRecordacao.create().show()
            }
        )

        binding.memoriaLista.adapter = adapter
        binding.memoriaLista.layoutManager = LinearLayoutManager(activity)

        viewModel.getMemorias().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitList(it.reversed())
                adapter.notifyDataSetChanged()
            }
        }

        val btnLogoff = binding.btnSair

        btnLogoff.setOnClickListener {
            requireActivity().lifecycleScope.launch {
                userViewModel.logoff()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}