package com.example.musicchaser.managementeventdetail.addperformer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentManagementEventDetailAddPerformerBinding
import com.example.musicchaser.databinding.FragmentManagementEventDetailEditBinding
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditFragmentArgs
import com.example.musicchaser.managementeventdetail.edit.ManagementEventDetailEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventDetailAddPerformerFragment : Fragment() {

    private val viewModel: ManagementEventDetailAddPerformerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentManagementEventDetailAddPerformerBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event = ManagementEventDetailAddPerformerFragmentArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event



        return binding.root
    }
}