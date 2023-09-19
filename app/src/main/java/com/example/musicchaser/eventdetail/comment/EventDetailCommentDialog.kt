package com.example.musicchaser.eventdetail.comment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogBasicInfoEditBinding
import com.example.musicchaser.databinding.DialogEventDetailCommentBinding
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import com.example.musicchaser.profile.basicinfoedit.BasicInfoEditViewModel
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext

@AndroidEntryPoint
class EventDetailCommentDialog : AppCompatDialogFragment() {

    private val viewModel: EventDetailCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogEventDetailCommentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event = EventDetailFragmentArgs.fromBundle(requireArguments()).selectedEvent
        viewModel.event = event

        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        // setting post button
        binding.eventDetailCommentPostButton.setOnClickListener {
            if ( binding.eventDetailCommentEditArea.text.toString() != "") {
                viewModel.postCommentForEvent()
                viewModel.addCommentAmounts()
                dismiss()
            } else {
                Toast.makeText(context,"Please fill in your comment :D", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }
}