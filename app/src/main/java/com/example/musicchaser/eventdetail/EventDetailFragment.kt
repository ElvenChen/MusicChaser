package com.example.musicchaser.eventdetail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventBinding
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.event.EventListAdapter
import com.example.musicchaser.event.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private val viewModel: EventDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentEventDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // throwing nav-arg to viewModel
        val event = EventDetailFragmentArgs.fromBundle(requireArguments()).selectedEvent
        viewModel.event = event

        // check this event isFavorite = ture or false, to adjust favorite button show/hide
        viewModel.getIfEventIsFavorite()

        // getting event comments
        viewModel.getEventCommentListWithNoAuthorName()

        // getting setting comment recyclerView adapter
        binding.eventDetailCommentsRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = EventCommentListAdapter()
        binding.eventDetailCommentsRecyclerView.adapter = adapter




        //observing dataListWithNoAuthorName for making next function call to transfer author ID into author's nickname
        viewModel.dataListForGetAuthorNameCall.observe(viewLifecycleOwner, Observer {
            Log.i("EventTest", "Observe dataListForGetAuthorNameCall from Fragment = $it")
            viewModel.getCompletedEventCommentList(it)
        })

        //observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })




        // setting add to favorite event function
        binding.eventDetailAddFavoriteButton.setOnClickListener {
            viewModel.addFavoriteEvent()
        }

        // setting delete favorite event function
        binding.eventDetailAddFavoriteButtonDone.setOnClickListener {
            viewModel.deleteFavoriteEvent()
        }

        // observing event isFavorite
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if(it == true){
                Log.i("EventTest", " isFavorite = $it ")
                binding.eventDetailAddFavoriteButton.visibility = View.GONE
                binding.eventDetailAddFavoriteButtonDone.visibility = View.VISIBLE
            } else {
                Log.i("EventTest", " isFavorite = $it ")
                binding.eventDetailAddFavoriteButton.visibility = View.VISIBLE
                binding.eventDetailAddFavoriteButtonDone.visibility = View.GONE
            }
        })





        // setting navigation
        binding.eventDetailBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.eventDetailEditButton.setOnClickListener {
            findNavController().navigate(EventDetailFragmentDirections.navigateToEventdetailCommentDialog(event))
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}