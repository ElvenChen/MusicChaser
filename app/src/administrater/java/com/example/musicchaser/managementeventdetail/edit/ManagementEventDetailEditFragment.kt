package com.example.musicchaser.managementeventdetail.edit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementEventDetailEditTimePopupBinding
import com.example.musicchaser.databinding.FragmentManagementArtistDetailEditBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.ext.toFormattedDay
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerDay
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerHour
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerMinute
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerMonth
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerYear
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditFragmentArgs
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditFragmentDirections
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ManagementEventDetailEditFragment : Fragment() {

    private val viewModel: ManagementEventDetailEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventDetailEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event =
            ManagementEventDetailEditFragmentArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event

        // setting two-way binding for view
        viewModel.prepareEditFieldForView()


        // setting first submitting button to pop up reminder dialog to check again
        binding.managementEventEditDetailEditButton.setOnClickListener {
            binding.managementEventEditDetailEditButton.visibility = View.GONE
            binding.managementEventEditDetailReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really submit the changing to cloud
        binding.managementEventEditDetailEditConfirmButton.setOnClickListener {
            viewModel.editSelectedEvent()
            findNavController().navigate(ManagementEventDetailEditFragmentDirections.actionManagementEventDetailEditFragmentToManagementEventFragment())
        }



        // setting navigation
        binding.managementEventEditDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.eventDateEditButton.setOnClickListener {
            showDialog()
        }




        return binding.root
    }

    private fun showDialog() {
        val dialog = Dialog(this.requireContext())
        val binding: DialogManagementEventDetailEditTimePopupBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_management_event_detail_edit_time_popup,
            null,
            false
        )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)


        // setting DatePicker and TimePicker Init
        binding.datePicker.init(
            viewModel.eventDate.value!!.toFormattedTimeOfSettingInitDatePickerYear(),
            0,
            viewModel.eventDate.value!!.toFormattedTimeOfSettingInitDatePickerDay(),
            null
        )
        Log.i("ElvenTest", "timestamp:${viewModel.eventDate.value!!.toFormattedTimeOfSettingInitDatePickerMonth()}")

        binding.timePicker.setIs24HourView(false)
        binding.timePicker.hour = viewModel.eventDate.value!!.toFormattedTimeOfSettingInitDatePickerHour()
        binding.timePicker.minute = viewModel.eventDate.value!!.toFormattedTimeOfSettingInitDatePickerMinute()


        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.NO_GRAVITY)

        binding.lifecycleOwner = viewLifecycleOwner


        // setting navigation
        binding.managementEventEditDetailCancelButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.managementEventEditDetailConfirmButton.setOnClickListener {
            // setting timer getting logic
            val year = binding.datePicker.year
            val month = binding.datePicker.month
            val day = binding.datePicker.dayOfMonth
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hour, minute)

            val timestamp = calendar.timeInMillis
            Log.i("ElvenTest", "timestamp:$timestamp")

            viewModel.eventDate.value = (timestamp / 1000)
            dialog.dismiss()
        }
    }
}