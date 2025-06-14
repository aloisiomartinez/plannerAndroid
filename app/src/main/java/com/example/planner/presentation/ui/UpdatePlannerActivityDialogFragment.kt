package com.example.planner.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.planner.R
import com.example.planner.databinding.FragmentInitialLoadingBinding
import com.example.planner.databinding.FragmentUpdatePlannerActivityDialogBinding
import com.example.planner.domain.model.PlannerActivity
import com.example.planner.domain.utils.createCalendarFromTimeMillis
import com.example.planner.domain.utils.toPlannerActivityDate
import com.example.planner.domain.utils.toPlannerActivityTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class UpdatePlannerActivityDialogFragment(
    private val selectedActivity: PlannerActivity
) : BottomSheetDialogFragment() {

    private var _binding: FragmentUpdatePlannerActivityDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdatePlannerActivityDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val selectedActivityDateTimeCalendar =
                createCalendarFromTimeMillis(timeMillis = selectedActivity.datetime)

            tietUpdatedPlannerActivityName.setText(selectedActivity.name)
            tietUpdatedPlannerActivityDate.setText(selectedActivityDateTimeCalendar.toPlannerActivityDate())
            tietUpdatedPlannerActivityTime.setText(selectedActivityDateTimeCalendar.toPlannerActivityTime())

        }
    }

    companion object {
        const val TAG = "UpdatePlannerActivityDialogFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}