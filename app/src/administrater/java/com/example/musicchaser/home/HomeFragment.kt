package com.example.musicchaser.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.databinding.FragmentHomeBinding
import com.example.musicchaser.managementevent.ManagementEventViewModel
import com.example.musicchaser.profile.ProfileFragmentDirections
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        // setting navigation
        binding.managementHomePageEvent.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.navigateToManagementEventFragment())
        }

        binding.managementHomePageArtist.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.navigateToManagementArtistFragment())
        }

        binding.managementHomePageUser.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.navigateToManagementUserFragment())
        }

        return binding.root
    }
}