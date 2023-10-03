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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentEventBinding
import com.example.musicchaser.databinding.FragmentEventDetailBinding
import com.example.musicchaser.event.EventListAdapter
import com.example.musicchaser.event.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private val viewModel: EventDetailViewModel by viewModels()

    //    private var job: Job? = null
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
//        job = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
//            while (true) {
//                viewModel.getEventCommentListWithNoAuthorName()
//                delay(10000L)
//            }
//        }

        // getting event performers
        viewModel.getEventPerformerListWithNoArtistName()


        // setting drop down refresh all comments
        val listener = SwipeRefreshLayout.OnRefreshListener {
            Log.i("Fire store", "Call drop down refresh API")
            viewModel.getEventCommentListWithNoAuthorName()
            binding.layoutSwipeRefreshArticles.isRefreshing = true
            Log.i("Fire store", "drop down refresh API Call Done")
        }
        binding.layoutSwipeRefreshArticles.setOnRefreshListener(listener)



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

        //observing comments list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            binding.layoutSwipeRefreshArticles.isRefreshing = false
        })

        //observing dataListWithNoArtistName for making next function call to transfer artist ID into artist's name
        viewModel.dataListForGetArtistNameCall.observe(viewLifecycleOwner, Observer {
            Log.i("EventPerformerTest", "Observe dataListWithNoArtistName from Fragment = $it")
            viewModel.getCompletedEventPerformerList(it)
        })

        //observing performers list for View
        viewModel.performerDataListForView.observe(viewLifecycleOwner, Observer {
            binding.eventDetailAttendantContent.text = it
        })



        // setting add to favorite event function
        binding.eventDetailAddFavoriteButton.setOnClickListener {
            viewModel.addFavoriteEvent()
            findNavController().navigate(EventDetailFragmentDirections.navigateToPopUpMessageDialog(
                0,"收藏成功"
            ))
        }

        // setting delete favorite event function
        binding.eventDetailAddFavoriteButtonDone.setOnClickListener {
            viewModel.deleteFavoriteEvent()
            findNavController().navigate(EventDetailFragmentDirections.navigateToPopUpMessageDialog(
                1,"取消收藏"
            ))
        }

        // observing event isFavorite
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if (it == true) {
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
            findNavController().navigate(
                EventDetailFragmentDirections.navigateToEventdetailCommentDialog(
                    event
                )
            )
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        job?.cancel()
//        Log.i("EventTest", "Job cancel")
//    }
}