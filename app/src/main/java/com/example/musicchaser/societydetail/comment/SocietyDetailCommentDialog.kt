package com.example.musicchaser.societydetail.comment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogEventDetailCommentBinding
import com.example.musicchaser.databinding.DialogSocietyDetailCommentBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import com.example.musicchaser.eventdetail.comment.EventDetailCommentViewModel
import com.example.musicchaser.society.post.SocietyPostDialogDirections
import com.example.musicchaser.societydetail.SocietyDetailFragmentArgs
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocietyDetailCommentDialog : AppCompatDialogFragment() {

    private val viewModel: SocietyDetailCommentViewModel by viewModels()

    var popUpCommentSuccessDialog : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogSocietyDetailCommentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting loading fade in animation
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(500).start()

        // throwing nav-arg to viewModel
        val thread = SocietyDetailFragmentArgs.fromBundle(requireArguments()).selectedThread
        viewModel.thread = thread



        // setting post button
        binding.societyDetailCommentPostButton.setOnClickListener {
            if ( binding.societyDetailCommentEditArea.text.toString() != "") {
                viewModel.postCommentForThread()
                viewModel.addThreadCommentAmounts()
                popUpCommentSuccessDialog = true
                dismiss()
            } else {
                Toast.makeText(context,"Please fill in your comment :D", Toast.LENGTH_LONG).show()
            }
        }



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (popUpCommentSuccessDialog){
            findNavController().navigate(
                SocietyDetailCommentDialogDirections.navigateToPopUpMessageDialog(
                    0, "留言成功"
                )
            )
        }
    }
}