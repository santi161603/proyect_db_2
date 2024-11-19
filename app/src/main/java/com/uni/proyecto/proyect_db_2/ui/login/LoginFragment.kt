package com.uni.proyecto.proyect_db_2.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uni.proyecto.proyect_db_2.R
import com.uni.proyecto.proyect_db_2.data.models.LoginDTO
import com.uni.proyecto.proyect_db_2.databinding.FragmentLoginBinding
import com.uni.proyecto.proyect_db_2.databinding.FragmentRegisterBinding
import com.uni.proyecto.proyect_db_2.ui.register.RegisterViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnIniciarSesion.setOnClickListener {
            if (binding.editTextCorreo.text.toString().isNotEmpty() && binding.editTextClave.text.toString().isNotEmpty()) {
                val loginDTO = LoginDTO(
                    correo = binding.editTextCorreo.text.toString(),
                    clave = binding.editTextClave.text.toString()
                )

                viewModel.loginUsuario(loginDTO)

            }
        }
        binding.btnRegistrar.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        observerConfig()

        return root
    }

    private fun observerConfig() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is LoginViewModel.LoginUiState.Loading -> {
                    // Mostrar un ProgressBar o similar
                    binding.btnIniciarSesion.isEnabled = false
                }
                LoginViewModel.LoginUiState.HideLoading -> {
                    binding.btnIniciarSesion.isEnabled = true
                }
                is LoginViewModel.LoginUiState.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
                }
                is LoginViewModel.LoginUiState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}