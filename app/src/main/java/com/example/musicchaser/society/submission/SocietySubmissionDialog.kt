package com.example.musicchaser.society.submission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.artistdetail.ArtistDetailViewModel
import com.example.musicchaser.databinding.DialogSocietyPostBinding
import com.example.musicchaser.databinding.DialogSocietySubmissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocietySubmissionDialog : AppCompatDialogFragment() {

    private val viewModel: SocietySubmissionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogSocietySubmissionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }
}