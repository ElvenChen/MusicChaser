package com.example.musicchaser.managementuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import com.example.musicchaser.databinding.FragmentManagementUserBinding
import com.example.musicchaser.managementevent.ManagementEventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementUserFragment : Fragment() {

    private val viewModel: ManagementUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementUserBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}