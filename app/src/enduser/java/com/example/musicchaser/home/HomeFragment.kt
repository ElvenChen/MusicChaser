package com.example.musicchaser.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

//    private val viewModel: HomeViewModel by lazy {
//        ViewModelProvider(this)[HomeViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }
}