package com.example.musicchaser.managementeventdetail.delete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementEventDetailDeleteBinding
import com.example.musicchaser.managementartistdetail.delete.ManagementArtistDetailDeleteDialogArgs
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

        // throwing nav-arg to viewModel
        val event = ManagementEventDetailDeleteDialogArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}