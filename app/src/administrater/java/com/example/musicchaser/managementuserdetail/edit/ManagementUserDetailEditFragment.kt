package com.example.musicchaser.managementuserdetail.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.databinding.FragmentManagementUserDetailEditBinding
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditFragmentArgs
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



        return binding.root
    }
}