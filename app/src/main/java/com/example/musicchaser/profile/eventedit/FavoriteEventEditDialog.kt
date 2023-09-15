package com.example.musicchaser.profile.eventedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogFavoriteEventEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteEventEditDialog : AppCompatDialogFragment() {

    private val viewModel: FavoriteEventEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogFavoriteEventEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }


        return binding.root
    }
}