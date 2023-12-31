package com.example.musicchaser.popupmessage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.NavigationDirections
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogPopUpMessageBinding
import com.example.musicchaser.databinding.DialogSocietyDetailCommentBinding
import com.example.musicchaser.societydetail.SocietyDetailFragmentArgs
import com.example.musicchaser.societydetail.comment.SocietyDetailCommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopUpMessageDialog : AppCompatDialogFragment() {

    private val viewModel: PopUpMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogPopUpMessageBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting loading fade in animation
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(500).start()

        // grab navigation sage-arg
        val messageIconType = PopUpMessageDialogArgs.fromBundle(requireArguments()).messageIconType
        val messageContent = PopUpMessageDialogArgs.fromBundle(requireArguments()).messageContent



        // setting messageIconType
        if(messageIconType == 0){
            binding.popUpMessageCheckIcon.visibility = View.VISIBLE
            binding.popUpMessageUncheckIcon.visibility = View.GONE
        } else {
            binding.popUpMessageCheckIcon.visibility = View.GONE
            binding.popUpMessageUncheckIcon.visibility = View.VISIBLE
        }

        // setting messageContent
        binding.popUpMessageTitle.text = messageContent



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        val destinationId = NavHostFragment.findNavController(this).currentDestination?.id

        val navBackStackEntry = NavHostFragment.findNavController(this).getBackStackEntry(destinationId ?: 0)
        val sourceFragment = navBackStackEntry.destination

        // reloading profile fragment page
        if (sourceFragment.label == "ProfileFragment"){
            findNavController().navigate(NavigationDirections.navigateToProfileFragment())
        }

    }
}