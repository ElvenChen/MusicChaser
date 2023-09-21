package com.example.musicchaser.managementeventdetail.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementArtistDetailEditBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditFragmentArgs
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        val event = ManagementEventDetailEditFragmentArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event




        return binding.root
    }
}