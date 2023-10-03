package com.example.musicchaser.profile.basicinfoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogBasicInfoEditBinding
import com.example.musicchaser.societydetail.comment.SocietyDetailCommentDialogDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasicInfoEditDialog : AppCompatDialogFragment() {

    private val viewModel: BasicInfoEditViewModel by viewModels()

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
        val binding = DialogBasicInfoEditBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        // setting finish button
        binding.basicInfoSaveButton.setOnClickListener {
            if ( binding.basicInfoEditArea.text.toString() != "") {
                viewModel.finishNicknameEdit()
                popUpCommentSuccessDialog = true
                dismiss()
            } else {
                Toast.makeText(context,"Please fill in your nickname :D",Toast.LENGTH_LONG).show()
            }
        }



        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        if (popUpCommentSuccessDialog){
            findNavController().navigate(
                BasicInfoEditDialogDirections.navigateToPopUpMessageDialog(
                    0, "編輯成功"
                )
            )
        }
    }
}