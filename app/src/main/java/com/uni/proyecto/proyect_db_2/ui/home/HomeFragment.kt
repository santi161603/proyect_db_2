package com.uni.proyecto.proyect_db_2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uni.proyecto.proyect_db_2.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.CallableStatement
import java.sql.SQLException
import kotlin.math.log

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonObtenerTexto.setOnClickListener {
            if(binding.editTextInput.text.toString().isNotEmpty()) {
                viewModel.obtenerTexto(binding.editTextInput.text.toString())
            }else{
                Toast.makeText(requireContext(), "Ingrese un nombre", Toast.LENGTH_LONG).show()
            }
        }

        observerConfig()
        return root
    }

    private fun observerConfig() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeViewModel.HomeUiState.Loading -> {
                    // Mostrar un ProgressBar o similar
                    binding.progressBar.visibility = View.VISIBLE
                }
                HomeViewModel.HomeUiState.HideLoading -> {
                    // Ocultar el ProgressBar
                    binding.progressBar.visibility = View.GONE
                }
                is HomeViewModel.HomeUiState.Success -> {
                    // Mostrar el texto obtenido
                    binding.textHome.text = it.data
                }
                is HomeViewModel.HomeUiState.Error -> {
                    Log.e("HomeFragment", "Error: ${it.message}")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }


}