package com.uni.proyecto.proyect_db_2.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.uni.proyecto.proyect_db_2.R
import com.uni.proyecto.proyect_db_2.data.models.RegisterDTO
import com.uni.proyecto.proyect_db_2.databinding.FragmentHomeBinding
import com.uni.proyecto.proyect_db_2.databinding.FragmentRegisterBinding
import com.uni.proyecto.proyect_db_2.ui.home.HomeViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnRegistrar.setOnClickListener {
            if (binding.etNombreUsuario.text.toString().isNotEmpty() && binding.etCorreo.text.toString().isNotEmpty() &&
                binding.etContrasena.text.toString().isNotEmpty()) {

                val registerDTO = RegisterDTO(
                    afiliadoDirectoId = if (binding.etAfiliado.text.toString().isNotEmpty())
                        binding.etAfiliado.text.toString().toLong()
                    else
                        null,
                    nombreUsuario = binding.etNombreUsuario.text.toString(),
                    clave = binding.etContrasena.text.toString(),
                    correo = binding.etCorreo.text.toString(),
                    telefono = binding.etTelefono.text.toString().ifEmpty { null },
                    direccion = binding.etDireccion.text.toString().ifEmpty { null }
                )

                viewModel.registerUsuario(registerDTO)

            }
        }

        observerConfig()
        return root
    }

    private fun observerConfig() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is RegisterViewModel.RegisterUiState.Error -> {
                    Log.e("RegisterFragment", "Error: ${it.message}")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                RegisterViewModel.RegisterUiState.HideLoading -> {

                }
                RegisterViewModel.RegisterUiState.Loading -> {

                }
                is RegisterViewModel.RegisterUiState.Success -> {
                    Log.e("RegisterFragment", "Error: ${it.data}")
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}