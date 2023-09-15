package com.example.musicchaser.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.databinding.FragmentProfileBinding
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


        // setting navigation
        binding.profileBasicInfoEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToBasicInfoDialog())
        }

        binding.profileFavoriteEventEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToFavoriteEventEditDialog())
        }

        binding.profileFavoriteArtistEditButton.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.navigateToFavoriteArtistEditDialog())
        }


        return binding.root
    }
}