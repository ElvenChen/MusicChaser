package com.example.musicchaser.artistdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogArtistDetailBinding
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailDialog : AppCompatDialogFragment() {

    private val viewModel: ArtistDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogArtistDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val artist = ArtistDetailDialogArgs.fromBundle(requireArguments()).selectedArtist
        viewModel.artist = artist






        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}