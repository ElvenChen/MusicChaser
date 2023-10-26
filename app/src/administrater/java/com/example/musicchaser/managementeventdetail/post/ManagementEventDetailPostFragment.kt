package com.example.musicchaser.managementeventdetail.post

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
import com.example.musicchaser.databinding.FragmentManagementArtistDetailPostBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailPostBinding
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerDay
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerHour
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerMinute
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerMonth
import com.example.musicchaser.ext.toFormattedTimeOfSettingInitDatePickerYear
import com.example.musicchaser.managementartistdetail.post.ManagementArtistDetailPostFragmentDirections
import com.example.musicchaser.managementartistdetail.post.ManagementArtistDetailPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ManagementEventDetailPostFragment : Fragment() {

    private val viewModel: ManagementEventDetailPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventDetailPostBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting first submitting button to pop up reminder dialog to check again
        binding.managementEventPostDetailPostButton.setOnClickListener {
            binding.managementEventPostDetailPostButton.visibility = View.GONE
            binding.managementEventPostDetailReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really submit the new post to cloud
        binding.managementEventPostDetailPostConfirmButton.setOnClickListener {
            viewModel.postNewEvent()
            findNavController().navigate(ManagementEventDetailPostFragmentDirections.actionManagementEventDetailPostFragmentToManagementEventFragment())
        }



        // setting navigation
        binding.managementEventPostDetailBackButton.setOnClickListener {
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