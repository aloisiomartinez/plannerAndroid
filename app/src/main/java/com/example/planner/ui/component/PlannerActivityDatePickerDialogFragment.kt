package com.example.planner.ui.component

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.planner.R
import java.util.Calendar

class PlannerActivityDatePickerDialogFragment(
    private val onConfirm: (Int, Int, Int) -> Unit,
    private val onCancel: () -> Unit
): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val customDatePickerDialog =
            DatePickerDialog(
                requireContext(),
                this,
                year,
                month,
                day
            ).setupPlannerDatePicker(minDate = calendar.timeInMillis)
        return customDatePickerDialog
    }

    fun DatePickerDialog.setupPlannerDatePicker(minDate: Long) : DatePickerDialog =
        this.apply {
            datePicker.minDate = minDate

            window?.decorView?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lime_950))

            datePicker.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            setButton(
                DialogInterface.BUTTON_POSITIVE,
                "Confirm"
            ) { _, _ ->
                onConfirm(datePicker.year, datePicker.month, datePicker.dayOfMonth)
            }

            setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "Cancelar"
            ) { _, _ ->
                onCancel()
            }

        }

    override fun onDateSet(
        view: DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        // Só seria utilizado caso não houvesse a sobreescrita do botão primário de confirmação
    }

    companion object {
        const val TAG = "PlannerActivityDatePickerDialogFragment"
    }


}