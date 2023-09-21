package com.example.musicchaser.managementartistdetail.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementArtistDetailDeleteBinding
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
        val artist = ManagementArtistDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditArtist
        viewModel.artist = artist



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}