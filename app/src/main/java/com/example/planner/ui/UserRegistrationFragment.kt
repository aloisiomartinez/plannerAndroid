package com.example.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.planner.databinding.FragmentUserRegistrationBinding

class UserRegistrationFragment : Fragment() {
    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // TODO: Lógica da tela de cadastro do usuário
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}