package com.example.musicchaser.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.musicchaser.R
import com.example.musicchaser.databinding.FragmentHomeBinding
import com.example.musicchaser.event.EventViewModel
import com.example.musicchaser.profile.FavoriteEventListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // handle binding
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // setting hot event recyclerView adapter
        binding.homeHotEventRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val hotEventAdapter = HomeHotEventListAdapter()
        binding.homeHotEventRecyclerView.adapter = hotEventAdapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.homeHotEventRecyclerView)

        // setting hot event dotted recyclerView adapter
        binding.homeHotEventDottedRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val hotEventDottedAdapter = HomeHotEventDottedListAdapter()
        binding.homeHotEventDottedRecyclerView.adapter = hotEventDottedAdapter



        // observing list for adapter
        viewModel.dataListForAdapter.observe(viewLifecycleOwner, Observer { hotEventList ->
            hotEventAdapter.submitList(hotEventList)
            hotEventAdapter.notifyDataSetChanged()
            binding.homeHotEventRecyclerView.scrollToPosition(hotEventList.size * 1000)


            viewModel.galleryDotAmount = hotEventList.size
        })



        // setting dotted recycler view to move with infinite hot event recycler view
        var currentItemPlace = 0
        binding.homeHotEventRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val currentPosition = layoutManager.findLastVisibleItemPosition() -1

                if (currentItemPlace != currentPosition) {
                    Log.i("detailDot", "currentPosition = $currentPosition")
                    viewModel.getListForGalleryDotAdapter(currentPosition)
                    currentItemPlace = currentPosition
                    Log.i("detailDot", "currentItemPlace = $currentItemPlace")
                }
            }
        })

        // observing dotted list for adapter
        viewModel.listForGalleryDotAdapter.observe(viewLifecycleOwner, Observer {
            Log.i("detailDot", "listForGalleryDotAdapter : $it")
            hotEventDottedAdapter.submitList(it)
            hotEventDottedAdapter.notifyDataSetChanged()
        })




        return binding.root
    }
}