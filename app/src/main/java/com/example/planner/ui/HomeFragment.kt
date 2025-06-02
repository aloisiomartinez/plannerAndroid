package com.example.planner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.planner.R
import com.example.planner.data.utils.imageBase64ToBitmap
import com.example.planner.databinding.FragmentHomeBinding
import com.example.planner.ui.viewmodel.UserRegistrationViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    val userRegistrationViewModel: UserRegistrationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        with(binding) {
            btnSaveNewPlannerActivity.setOnClickListener {
                UpdatePlannerActivityDialogFragment().show(
                    childFragmentManager,
                    UpdatePlannerActivityDialogFragment.TAG
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            userRegistrationViewModel.profile.collect { profile ->
                binding.tvUserName.text = getString(R.string.ola_usuario, profile.name)
                imageBase64ToBitmap(base64String = profile.image)?.let { imageBitmap ->
                    binding.ivUserPhoto.setImageBitmap(imageBitmap)
                }


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}