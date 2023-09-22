package com.example.musicchaser.managementartistdetail.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementArtistDetailDeleteBinding
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementArtistDetailDeleteDialog : AppCompatDialogFragment() {

    private val viewModel: ManagementArtistDetailDeleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogManagementArtistDetailDeleteBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val artist =
            ManagementArtistDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditArtist
        viewModel.artist = artist


        // setting first submitting button to pop up reminder dialog to check again
        binding.managementArtistDeleteDetailDeleteButton.setOnClickListener {
            binding.managementArtistDeleteDetailDeleteButton.visibility = View.GONE
            binding.managementArtistDeleteDetailReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really delete and update to cloud
        binding.managementArtistDeleteDetailDeleteConfirmButton.setOnClickListener {
            viewModel.deleteSelectedArtist()
            findNavController().navigate(ManagementArtistDetailDeleteDialogDirections.actionManagementArtistDetailDeleteDialogToManagementArtistFragment())
        }


        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}