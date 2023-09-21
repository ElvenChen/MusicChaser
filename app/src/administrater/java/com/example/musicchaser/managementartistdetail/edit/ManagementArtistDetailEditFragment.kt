package com.example.musicchaser.managementartistdetail.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.databinding.FragmentManagementArtistDetailEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementArtistDetailEditFragment : Fragment() {

    private val viewModel: ManagementArtistDetailEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementArtistDetailEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val artist = ManagementArtistDetailEditFragmentArgs.fromBundle(requireArguments()).selectedEditArtist
        viewModel.artist = artist
        

        return binding.root
    }
}