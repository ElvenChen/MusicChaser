package com.example.musicchaser.managementartistdetail.edit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        viewModel.artist= artist

        // setting two-way binding for view
        viewModel.prepareEditFieldForView()



        // setting first submitting button to pop up reminder dialog to check again
        binding.managementArtistEditDetailEditButton.setOnClickListener {
            binding.managementArtistEditDetailEditButton.visibility = View.GONE
            binding.managementArtistEditDetailReminderConstraint.visibility = View.VISIBLE
        }

        // setting second submitting button to really submit the changing to cloud
        binding.managementArtistEditDetailEditConfirmButton.setOnClickListener {
            viewModel.editSelectedArtist()
            findNavController().navigate(ManagementArtistDetailEditFragmentDirections.actionManagementArtistDetailEditFragmentToManagementArtistFragment())
        }



        // setting navigation
        binding.managementArtistEditDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }



        return binding.root
    }
}