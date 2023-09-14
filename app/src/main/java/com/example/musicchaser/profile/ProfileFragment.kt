package com.example.musicchaser.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.musicchaser.MainViewModel
import com.example.musicchaser.R
import com.example.musicchaser.databinding.ActivityMainBinding
import com.example.musicchaser.databinding.FragmentProfileBinding
import com.example.musicchaser.databinding.FragmentSocietyBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentProfileBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        return binding.root
    }
}