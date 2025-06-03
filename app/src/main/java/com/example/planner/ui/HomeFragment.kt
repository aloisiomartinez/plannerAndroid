package com.example.planner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.planner.R
import com.example.planner.domain.utils.imageBase64ToBitmap
import com.example.planner.databinding.FragmentHomeBinding
import com.example.planner.ui.component.PlannerActivityDatePickerDialogFragment
import com.example.planner.ui.component.PlannerActivityTimePickerDialogFragment
import com.example.planner.ui.viewmodel.UserRegistrationViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    val userRegistrationViewModel: UserRegistrationViewModel by activityViewModels()


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
            tietNewPlannerActivityDate.setOnClickListener {
                PlannerActivityDatePickerDialogFragment(
                    onConfirm = { year, month, day ->
                        Toast.makeText(requireContext(), "$year $month $day", Toast.LENGTH_SHORT).show()
                    },
                    onCancel = {

                    }
                ).show(
                    childFragmentManager,
                    PlannerActivityDatePickerDialogFragment.TAG
                )
            }

            tietNewPlannerActivityTime.setOnClickListener {
                PlannerActivityTimePickerDialogFragment(
                    onConfirm = { hourOfDay, minute ->
                        Toast.makeText(requireContext(), "$hourOfDay $minute", Toast.LENGTH_SHORT).show()
                    },
                    onCancel = {

                    }
                ).show(
                    childFragmentManager,
                    PlannerActivityTimePickerDialogFragment.TAG
                )
            }

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
            launch {
                userRegistrationViewModel.profile.collect { profile ->
                    binding.tvUserName.text = getString(R.string.ola_usuario, profile.name)
                    imageBase64ToBitmap(base64String = profile.image)?.let { imageBitmap ->
                        binding.ivUserPhoto.setImageBitmap(imageBitmap)
                    }
                }
            }
            launch {
                userRegistrationViewModel.isTokenValid.distinctUntilChanged {
                    old, new -> old == new
                }.collect { isTokenValid ->
                    Log.d("CheckIsTokenValid", "setupObservers: isTokenValid = $isTokenValid")
                    if (isTokenValid == false) showNewTokenSnackBar()
                }
            }
        }
    }

    private fun showNewTokenSnackBar() {
        Snackbar.make(requireView(), "Oops, ... O seu token expirou", Snackbar.LENGTH_INDEFINITE)
            .setAction("OBTER NOVO TOKEN") {
                userRegistrationViewModel.obtainNewToken()
            }
            .setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.lime_300
                )
            ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}