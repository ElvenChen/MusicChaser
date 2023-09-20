package com.example.musicchaser.managementartist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementArtistBinding
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import com.example.musicchaser.managementevent.ManagementEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementArtistFragment : Fragment() {

    private val viewModel: ManagementArtistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementArtistBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}