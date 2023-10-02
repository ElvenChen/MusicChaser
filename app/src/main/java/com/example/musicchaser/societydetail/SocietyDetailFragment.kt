package com.example.musicchaser.societydetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.databinding.FragmentSocietyDetailBinding
import com.example.musicchaser.eventdetail.EventCommentListAdapter
import com.example.musicchaser.eventdetail.EventDetailFragmentArgs
import com.example.musicchaser.eventdetail.EventDetailFragmentDirections
import com.example.musicchaser.eventdetail.EventDetailViewModel
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocietyDetailFragment : Fragment() {

    private val viewModel: SocietyDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentSocietyDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val thread = SocietyDetailFragmentArgs.fromBundle(requireArguments()).selectedThread
        viewModel.thread = thread

        // getting thread comments
        viewModel.getThreadCommentList()

        // getting setting thread comment recyclerView adapter
        binding.societyDetailCommentsRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ThreadCommentListAdapter()
        binding.societyDetailCommentsRecyclerView.adapter = adapter





        //observing dataListWithNoAuthorName for making next function call to transfer author ID into author's nickname
        viewModel.dataListForGetAuthorNameCall.observe(viewLifecycleOwner, Observer {
            Log.i("ThreadCommentTest", "Observe dataListForGetAuthorNameCall from Fragment = $it")
            viewModel.getCompletedThreadCommentList(it)
        })

        //observing thread comments list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("ThreadCommentTest", "Observe dataListForAdapter from Fragment = $it")
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })





        // setting navigation
        binding.societyDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.societyDetailCommentButton.setOnClickListener {
            findNavController().navigate(
                SocietyDetailFragmentDirections.navigateToSocietydetailCommentDialog(
                    thread
                )
            )
        }



        return binding.root
    }
}