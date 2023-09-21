package com.example.musicchaser.managementartistdetail.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementArtistDetailEditBinding
import com.example.musicchaser.databinding.FragmentManagementArtistDetailPostBinding
import com.example.musicchaser.managementartistdetail.edit.ManagementArtistDetailEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementArtistDetailPostFragment : Fragment() {

    private val viewModel: ManagementArtistDetailPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementArtistDetailPostBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        return binding.root
    }
}