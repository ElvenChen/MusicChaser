package com.example.musicchaser.managementeventdetail.delete

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
import com.example.musicchaser.managementartistdetail.delete.ManagementArtistDetailDeleteDialogArgs
import com.example.musicchaser.managementartistdetail.delete.ManagementArtistDetailDeleteDialogDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventDetailDeleteDialog : AppCompatDialogFragment() {

    private val viewModel: ManagementEventDetailDeleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogManagementEventDetailDeleteBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting loading fade in animation
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(500).start()

        // throwing nav-arg to viewModel
        val event = ManagementEventDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event



        // setting first submitting button to pop up reminder dialog to check again
        binding.managementEventDeleteDetailDeleteButton.setOnClickListener {
            binding.managementEventDeleteDetailDeleteButton.visibility = View.GONE
            binding.managementEventDeleteDetailCancelButton.visibility = View.GONE
            binding.managementEventDeleteDetailReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really delete and update to cloud
        binding.managementEventDeleteDetailDeleteConfirmButton.setOnClickListener {
            viewModel.deleteSelectedEvent()
            findNavController().navigate(ManagementEventDetailDeleteDialogDirections.actionManagementEventDetailDeleteDialogToManagementEventFragment())
        }



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        binding.managementEventDeleteDetailCancelButton.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}