package com.example.musicchaser.event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventBinding
import com.example.musicchaser.databinding.FragmentHomeBinding


class EventFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentEventBinding.inflate(inflater)

        return binding.root
    }

}