package com.example.musicchaser.society.post


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogSocietyPostBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SocietyPostDialog : AppCompatDialogFragment() {

    private val viewModel: SocietyPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogSocietyPostBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        // setting drop down menu adapter
        val options = arrayOf("分享", "閒聊")
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_munu_society_post, options)
        binding.societyPostThreadTypeEditArea.setAdapter(adapter)



        // setting finish button
        binding.societyPostSaveButton.setOnClickListener {
            if (binding.societyPostThreadNameEditArea.text.toString() != "" &&
                binding.societyPostThreadTypeEditArea.text.toString() != "" &&
                binding.societyPostThreadContentEditArea.text.toString() != ""
            ) {
                viewModel.postThread()
                Toast.makeText(
                    context,
                    "Your Thread is post!",
                    Toast.LENGTH_LONG
                ).show()
                dismiss()
            } else {
                Toast.makeText(context, "Please fill in all fields :D", Toast.LENGTH_LONG)
                    .show()
            }
        }



        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}