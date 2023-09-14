package com.example.musicchaser.artist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentArtistBinding
import com.example.musicchaser.databinding.FragmentEventBinding

class ArtistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentArtistBinding.inflate(inflater)

        return binding.root
    }
}