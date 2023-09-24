package com.example.musicchaser.managementuserdetail.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.databinding.FragmentManagementUserDetailEditBinding
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditFragmentArgs
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementUserDetailEditFragment : Fragment() {

    private val viewModel: ManagementUserDetailEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementUserDetailEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val user = ManagementUserDetailEditFragmentArgs.fromBundle(requireArguments()).selectedEditUser
        viewModel.user = user

        // setting switch button init situation
        binding.SwitchButton.isChecked = viewModel.user?.userBanned == 1



        // setting switch button triggered logic
        binding.SwitchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.changeSelectedUserBannedSituation(1)
            } else {
                viewModel.changeSelectedUserBannedSituation(0)
            }
        }



        // setting navigation
        binding.managementUserEditDetailBackButton.setOnClickListener {
            findNavController().navigate(ManagementUserDetailEditFragmentDirections.actionManagementUserDetailEditFragmentToManagementUserFragment())
        }



        return binding.root
    }
}