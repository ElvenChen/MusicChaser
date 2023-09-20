package com.example.musicchaser.managementevent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.databinding.FragmentManagementEventBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventFragment : Fragment() {

    private val viewModel: ManagementEventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        return binding.root
    }
}