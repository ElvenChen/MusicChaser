package com.example.musicchaser.managementeventdetail.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementArtistDetailPostBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailPostBinding
import com.example.musicchaser.managementartistdetail.post.ManagementArtistDetailPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventDetailPostFragment : Fragment() {

    private val viewModel: ManagementEventDetailPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventDetailPostBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        return binding.root
    }
}