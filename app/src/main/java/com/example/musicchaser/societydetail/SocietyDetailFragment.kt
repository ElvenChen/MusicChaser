package com.example.musicchaser.societydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.databinding.FragmentSocietyDetailBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import com.example.musicchaser.eventdetail.EventDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocietyDetailFragment : Fragment() {

    private val viewModel: SocietyDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentSocietyDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val thread = SocietyDetailFragmentArgs.fromBundle(requireArguments()).selectedThread
        viewModel.thread = thread


        return binding.root
    }
}