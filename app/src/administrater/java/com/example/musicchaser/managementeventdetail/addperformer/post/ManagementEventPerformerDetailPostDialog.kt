package com.example.musicchaser.managementeventdetail.addperformer.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.DialogManagementEventPerformerDetailDeleteBinding
import com.example.musicchaser.databinding.DialogManagementEventPerformerDetailPostBinding
import com.example.musicchaser.managementeventdetail.addperformer.ManagementEventDetailAddPerformerListAdapter
import com.example.musicchaser.managementeventdetail.addperformer.delete.ManagementEventPerformerDetailDeleteDialogArgs
import com.example.musicchaser.managementeventdetail.addperformer.delete.ManagementEventPerformerDetailDeleteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagementEventPerformerDetailPostDialog : AppCompatDialogFragment() {

    private val viewModel: ManagementEventPerformerDetailPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.popUpDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = DialogManagementEventPerformerDetailPostBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event =
            ManagementEventPerformerDetailPostDialogArgs.fromBundle(requireArguments()).selectedEditEvent
        viewModel.event = event

        // setting recyclerView adapter
        binding.managementEventPerformerDetailPostRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ManagementEventPerformerDetailPostListAdapter(
            viewModel.deleteItemFromFilteredDataListAfterClickPost,
            viewModel.postEventPerformer,
            viewModel.postArtistAttendEvent
        )
        binding.managementEventPerformerDetailPostRecyclerView.adapter = adapter


        //observing unfilteredDataList for next filter function call
        viewModel.unfilteredDataListForFilter.observe(viewLifecycleOwner, Observer {
            Log.i("AddPerformerTest", "2. viewModel.getFilteredArtistList(it)")
            viewModel.getFilteredArtistList(it)
        })

        //observing filteredDataListForAdapter for recyclerView
        viewModel.filteredDataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i(
                "AddPerformerTest",
                "filteredDataListForAdapter = ${viewModel.filteredDataListForAdapter.value}"
            )
            if (it.isNotEmpty()){
                binding.managementEventPerformerDetailPostRecyclerViewDefaultMessage.visibility = View.GONE
            } else {
                binding.managementEventPerformerDetailPostRecyclerViewDefaultMessage.visibility = View.VISIBLE
            }
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })



        // setting search bar logic
        binding.managementEventPerformerDetailPostSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearchedArtistListResult(query)


                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == null || newText == "") {
                    viewModel.getSearchedArtistListResult("NeverGonnaHaveKeywordLikeThis")
                } else {
                    viewModel.getSearchedArtistListResult(newText)
                }
                return true
            }
        })


        // setting navigation
        binding.outerConstraint.setOnClickListener {
            dismiss()
        }

        binding.managementEventPerformerDetailPostCancelButton.setOnClickListener {
            dismiss()
        }



        return binding.root
    }
}