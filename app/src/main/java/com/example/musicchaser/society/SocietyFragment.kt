package com.example.musicchaser.society

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentArtistBinding
import com.example.musicchaser.databinding.FragmentSocietyBinding

class SocietyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentSocietyBinding.inflate(inflater)

        return binding.root
    }
}