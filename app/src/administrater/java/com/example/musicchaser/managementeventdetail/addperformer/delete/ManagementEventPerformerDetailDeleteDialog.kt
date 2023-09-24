package com.example.musicchaser.managementeventdetail.addperformer.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementEventDetailDeleteBinding
import com.example.musicchaser.databinding.DialogManagementEventPerformerDetailDeleteBinding
import com.example.musicchaser.managementeventdetail.delete.ManagementEventDetailDeleteDialogArgs
import com.example.musicchaser.managementeventdetail.delete.ManagementEventDetailDeleteDialogDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventPerformerDetailDeleteDialog : AppCompatDialogFragment() {

    private val viewModel: ManagementEventPerformerDetailDeleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogManagementEventPerformerDetailDeleteBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event = ManagementEventPerformerDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event= event

        val artist = ManagementEventPerformerDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditPerformer
        viewModel.artist = artist



        // setting first submitting button to pop up reminder dialog to check again
        binding.managementEventPerformerDetailDeleteButton.setOnClickListener {
            binding.managementEventPerformerDetailDeleteButton.visibility = View.GONE
            binding.managementEventPerformerDetailCancelButton.visibility = View.GONE
            binding.managementEventPerformerDetailDeleteReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really delete and update to cloud
        binding.managementEventPerformerDetailDeleteConfirmButton.setOnClickListener {
            viewModel.deleteEventPerformer()
            viewModel.deleteArtistAttendEvent()
            dismiss()
        }



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        binding.managementEventPerformerDetailCancelButton.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}